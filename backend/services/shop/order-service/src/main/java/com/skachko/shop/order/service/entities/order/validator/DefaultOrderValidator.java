package com.skachko.shop.order.service.entities.order.validator;

import com.skachko.shop.order.service.entities.order.dto.OrderRequest;
import com.skachko.shop.order.service.entities.order.validator.api.IOrderValidator;
import com.skachko.shop.order.service.exceptions.OrderValidationException;
import com.skachko.shop.order.service.libraries.utils.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Component
@RequiredArgsConstructor
public class DefaultOrderValidator implements IOrderValidator {
    private static final Pattern phonePattern = Pattern.compile("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
    private static final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    private static final String particularProductFieldFormatPattern = "products[%s].%s";

    private final MessageSource messageSource;

    @Override
    public void validateRequest(OrderRequest request) {
        List<OrderValidationException.StructuredError> errors = new ArrayList<>();

        addPaymentDetailsErrors(request.getPaymentDetails(), errors);

        if (request.getProducts() == null || request.getProducts().isEmpty()) {
            errors.add(
                    new OrderValidationException.StructuredError(
                            "products",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
            );
        } else {
            for (int i = 0; i < request.getProducts().size(); i++) {
                OrderRequest.ProductOrderRequest product = request.getProducts().get(i);
                if (product.getId() == null) {
                    errors.add(
                            new OrderValidationException.StructuredError(
                                    String.format(particularProductFieldFormatPattern, i, "product_id") ,
                                    messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
                    );
                }

                if (product.getQuantity() <= 0) {
                    errors.add(
                            new OrderValidationException.ValueStructuredError(
                                    String.format(particularProductFieldFormatPattern, i, "quantity") ,
                                    messageSource.getMessage("validation.field.less.value", new Object[]{1}, LocaleContextHolder.getLocale()),
                                    product.getQuantity()
                            )
                    );
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new OrderValidationException(errors);
        }

    }

    private void addPaymentDetailsErrors(OrderRequest.PaymentRequestDetails details, List<OrderValidationException.StructuredError> errors) {

        if (details == null) {
            errors.add(
                    new OrderValidationException.StructuredError(
                            "payment_details",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
            );
            return;
        }

        //TODO address validation after add to front
        //addAddressErrors(details.getBillingAddress(), errors);

        //TODO check expire date?
        if (CheckUtils.isNullOrEmpty(details.getExpireDate())) {
            errors.add(
                    new OrderValidationException.StructuredError(
                            "payment_details.payment_mode",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
            );
        }

        if (details.getPaymentMode() == null) {
            errors.add(
                    new OrderValidationException.StructuredError(
                            "payment_details.payment_mode",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
            );
        }

        //TODO ard number regex?
        if (CheckUtils.isNullOrEmpty(details.getCardNumber())) {
            errors.add(
                    new OrderValidationException.StructuredError(
                            "payment_details.card_number",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
            );
        }


        if (CheckUtils.isNullOrEmpty(details.getCardName())) {
            errors.add(
                    new OrderValidationException.StructuredError(
                            "payment_details.card_name",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
            );
        }
        if (CheckUtils.isNullOrEmpty(details.getPhone())) {
            errors.add(
                    new OrderValidationException.StructuredError(
                            "payment_details.phone",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
            );
        } else if (!phonePattern.matcher(details.getPhone()).matches()) {
            errors.add(
                    new OrderValidationException.ValueStructuredError(
                            "payment_details.phone",
                            messageSource.getMessage("validation.field.illegal.value", null, LocaleContextHolder.getLocale()),
                            details.getPhone()
                    )
            );
        }

        if (CheckUtils.isNullOrEmpty(details.getEmail())) {
            errors.add(
                    new OrderValidationException.StructuredError(
                            "payment_details.email",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale()))
            );
        } else if (!emailPattern.matcher(details.getEmail()).matches()) {
            errors.add(
                    new OrderValidationException.ValueStructuredError(
                            "payment_details.email",
                            messageSource.getMessage("validation.field.illegal.value", null, LocaleContextHolder.getLocale()),
                            details.getEmail()
                    )
            );
        }
    }

    private void addAddressErrors(OrderRequest.Address address, List<OrderValidationException.StructuredError> errors) {
        if (address == null) {
            errors.add(
                    new OrderValidationException.StructuredError(
                            "payment_details.billing_address",
                            messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale())
                    )
            );
        } else {

            if (CheckUtils.isNullOrEmpty(address.getAddressLineOne())) {
                errors.add(
                        new OrderValidationException.StructuredError(
                                "payment_details.billing_address.address_line_1",
                                messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale())
                        )
                );
            }

            if (CheckUtils.isNullOrEmpty(address.getCity())) {
                errors.add(
                        new OrderValidationException.StructuredError(
                                "payment_details.billing_address.city",
                                messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale())
                        )
                );
            }

            if (CheckUtils.isNullOrEmpty(address.getState())) {
                errors.add(
                        new OrderValidationException.StructuredError(
                                "payment_details.billing_address.state",
                                messageSource.getMessage("validation.field.empty", null, LocaleContextHolder.getLocale())
                        )
                );
            }

        }
    }
}
