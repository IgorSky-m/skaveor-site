package com.skachko.shop.payment.service.entities.payment.service;

import com.skachko.shop.payment.service.entities.payment.api.PaymentMode;
import com.skachko.shop.payment.service.entities.payment.dto.PaymentRequest;
import com.skachko.shop.payment.service.entities.payment.dto.PaymentResponse;
import com.skachko.shop.payment.service.entities.payment.dto.TransactionDetails;
import com.skachko.shop.payment.service.entities.payment.repository.api.ITransactionRepository;
import com.skachko.shop.payment.service.entities.payment.service.api.IPaymentHandlerService;
import com.skachko.shop.payment.service.entities.payment.validator.api.IPaymentValidator;
import com.skachko.shop.payment.service.exceptions.PaymentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentHandlerService implements IPaymentHandlerService {

    private final ITransactionRepository transactionDetailsRepository;
    private final IPaymentValidator paymentValidator;
    //TODO Mock
    @Override
    public UUID doPayment(PaymentRequest paymentRequest) {

        paymentValidator.validatePaymentDetails(paymentRequest);

        TransactionDetails transactionDetails
                = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();

        transactionDetails = transactionDetailsRepository.save(transactionDetails);

        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(UUID orderId) {

        TransactionDetails transactionDetails
                = transactionDetailsRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentException(
                        "TransactionDetails with given id not found",
                        "TRANSACTION_NOT_FOUND"));

        return PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .paymentDate(transactionDetails.getPaymentDate())
                .orderId(transactionDetails.getOrderId())
                .status(transactionDetails.getPaymentStatus())
                .amount(transactionDetails.getAmount())
                .build();
    }
}
