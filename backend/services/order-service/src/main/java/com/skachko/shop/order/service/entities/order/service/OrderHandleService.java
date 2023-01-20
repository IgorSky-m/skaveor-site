package com.skachko.shop.order.service.entities.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skachko.shop.order.service.entities.catalog.Item;
import com.skachko.shop.order.service.entities.catalog.client.ICatalogFeignClient;
import com.skachko.shop.order.service.entities.order.api.OrderStatus;
import com.skachko.shop.order.service.entities.order.dto.*;
import com.skachko.shop.order.service.entities.order.repository.api.IOrderProductDetailsRepository;
import com.skachko.shop.order.service.entities.order.repository.api.IOrderRepository;
import com.skachko.shop.order.service.entities.order.service.api.IOrderHandleService;
import com.skachko.shop.order.service.entities.order.validator.api.IOrderValidator;
import com.skachko.shop.order.service.entities.payment.client.api.IPaymentServiceClient;
import com.skachko.shop.order.service.entities.payment.dto.PaymentRequest;
import com.skachko.shop.order.service.entities.payment.dto.PaymentResponse;
import com.skachko.shop.order.service.exceptions.OrderException;
import com.skachko.shop.order.service.libraries.mvc.api.AEntity;
import com.skachko.shop.order.service.libraries.mvc.exceptions.ServiceException;
import com.skachko.shop.order.service.libraries.search.SearchCriteria;
import com.skachko.shop.order.service.libraries.search.SearchPredicate;
import com.skachko.shop.order.service.libraries.search.api.EComparisonOperator;
import com.skachko.shop.order.service.libraries.search.api.EPredicateOperator;
import com.skachko.shop.order.service.libraries.search.api.ISearchExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderHandleService implements IOrderHandleService {

    private final IOrderRepository orderRepository;
    private final IOrderValidator validator;
    private final IPaymentServiceClient paymentServiceClient;
    private final ICatalogFeignClient catalogFeignClient;

    private final IOrderProductDetailsRepository orderProductDetailsRepository;

    private final ObjectMapper mapper;

    //TODO AMOUNT & TOTAL_AMOUNT check in this method
    //TODO payment check
    @Override
    public PlacedOrderResponse placeOrder(OrderRequest orderRequest, String id) {
        System.out.println("user id: " + id);
        validator.validateRequest(orderRequest);

        Set<UUID> uuidsSet = orderRequest.getProducts()
                .stream()
                .map(OrderRequest.ProductOrderRequest::getId)
                .collect(Collectors.toCollection(HashSet::new));

        SearchCriteria cr = new SearchCriteria();
        SearchPredicate pr = new SearchPredicate();
        pr.setConditionOperator(EPredicateOperator.AND);
        pr.setSearchExpressions(new ArrayList<>() {{
            add(ISearchExpression.of(AEntity.ID, EComparisonOperator.IN, uuidsSet.toArray()));
        }});
        cr.setSearchPredicate(pr);
        List<Item> existedItems;
        //Check is product exists
        try {

            //TODO CHECK PRICE

            existedItems = catalogFeignClient.getAll(mapper.writeValueAsString(cr));

            if (uuidsSet.size() != existedItems.size()) {

                uuidsSet.removeAll(
                        existedItems.stream()
                                .map(Item::getId)
                                .collect(Collectors.toCollection(HashSet::new))
                );

                //TODO exception
                throw new ServiceException(
                        String.format(
                                "products %s for product order doesn't exist",
                                uuidsSet.stream()
                                        .map(UUID::toString)
                                        .collect(Collectors.joining(", "))
                        )
                );
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            //TODO Exception
            throw new ServiceException("can't read product info");
        }

        long totalAmount = 0;
        Map<UUID, Item> itemsMap = new HashMap<>();

        for (Item item : existedItems) {
            if (item.getPrice() != null) {
                totalAmount += item.getPrice();
            }
            itemsMap.put(item.getId(), item);
        }


        //TODO ADD WAREHOUSE count AND REDUCE FOR NON INFINITE PRODUCTS

        List<OrderProductDetails> products = orderRequest.getProducts().stream().map(e -> {
                    Long amount = itemsMap.get(e.getId()).getPrice();
                    return OrderProductDetails.builder()
                            .productId(e.getId())
                            .amount(amount != null ? amount : 0)
                            .quantity(e.getQuantity())
                            .build();
                }
        ).collect(Collectors.toCollection(ArrayList::new));

        //Save the data with Status CustomOrder Created
        CustomOrder customOrder = CustomOrder.builder()
                .status(OrderStatus.CREATED)
                .products(products)
                .orderDate(Instant.now())
                .totalAmount(totalAmount)
                .userId(id != null ? UUID.fromString(id) : null)
                .build();

        orderProductDetailsRepository.saveAll(products);

        customOrder = orderRepository.save(customOrder);


        //do payment
        PaymentRequest paymentRequest
                = PaymentRequest.builder()
                .orderId(customOrder.getId())
                .paymentMode(orderRequest.getPaymentDetails().getPaymentMode())
                .amount(totalAmount)
                .build();

        OrderStatus status;

        try {
            paymentServiceClient.doPayment(paymentRequest);
            status = OrderStatus.PLACED;
        } catch (Exception e) {
            status = OrderStatus.PAYMENT_FAILED;
        }

        customOrder.setStatus(status);

        //update data with new status
        orderRepository.save(customOrder);

        return new PlacedOrderResponse(customOrder.getId(), status);
    }

    @Override
    public OrderResponse getOrderDetails(UUID orderId) {

        CustomOrder customOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(
                        "CustomOrder not found for the customOrder Id:" + orderId,
                        "NOT_FOUND",
                        404
                ));

        Map<UUID, OrderProductDetails> productDetailsMap = customOrder.getProducts()
                .stream()
                .collect(Collectors.toMap(
                        OrderProductDetails::getProductId,
                        Function.identity(), (e, r) -> r));

        SearchCriteria cr = new SearchCriteria();
        SearchPredicate pr = new SearchPredicate();
        pr.setConditionOperator(EPredicateOperator.AND);
        pr.setSearchExpressions(new ArrayList<>() {{
            add(ISearchExpression.of(AEntity.ID, EComparisonOperator.IN, productDetailsMap.keySet().toArray()));
        }});
        cr.setSearchPredicate(pr);

        List<Item> items = null;
        try {

            items = catalogFeignClient.getAll(mapper.writeValueAsString(cr));
        } catch (Exception e) {
            //TODO exception with msg source
            throw new ServiceException("can't read ordered product list");
        }


        PaymentResponse paymentResponse = paymentServiceClient.getPaymentDetailsByOrderId(customOrder.getId())
                .getBody();

        List<OrderResponse.ProductDetails> productDetails = items.stream()
                .map(e -> {
                    final OrderProductDetails details = productDetailsMap.get(e.getId());
                    return OrderResponse.ProductDetails
                            .builder()
                            .title(e.getTitle())
                            .id(e.getId())
                            .titlePicture(e.getTitlePicture())
                            .type(e.getType())
                            .amount(details.getAmount())
                            .quantity(details.getQuantity())
                            .build();
                })
                .toList();

        OrderResponse.PaymentDetails paymentDetails
                = OrderResponse.PaymentDetails
                .builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentStatus(paymentResponse.getStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();

        return OrderResponse.builder()
                .orderId(customOrder.getId())
                .orderStatus(customOrder.getStatus())
                .amount(customOrder.getTotalAmount())
                .orderDate(customOrder.getOrderDate())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();
    }
}
