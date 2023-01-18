package com.skachko.shop.order.service.entities.payment.dto;


import com.skachko.shop.order.service.libraries.mvc.api.AEntity;
import lombok.*;

import java.time.Instant;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetails extends AEntity {

    private UUID orderId;

    private String paymentMode;

    private String referenceNumber;

    private Instant paymentDate;

    private String paymentStatus;

    private long amount;
}
