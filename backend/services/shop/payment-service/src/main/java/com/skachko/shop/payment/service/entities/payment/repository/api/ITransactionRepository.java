package com.skachko.shop.payment.service.entities.payment.repository.api;

import com.skachko.shop.payment.service.entities.payment.dto.TransactionDetails;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Optional;
import java.util.UUID;

public interface ITransactionRepository extends JpaRepositoryImplementation<TransactionDetails, UUID> {
    Optional<TransactionDetails> findByOrderId(UUID orderId);
}
