package com.skachko.shop.order.service.entities.order.dto;

import com.skachko.shop.order.service.libraries.mvc.api.AEntity;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends AEntity {


    private UUID productId;

    private long quantity;

    private Instant orderDate;

    private String orderStatus;

    private long amount;
}
