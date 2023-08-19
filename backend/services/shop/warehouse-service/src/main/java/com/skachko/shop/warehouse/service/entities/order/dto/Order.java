package com.skachko.shop.warehouse.service.entities.order.dto;

import com.skachko.shop.warehouse.service.libraries.mvc.api.AEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order extends AEntity {


    private UUID productId;

    private long quantity;

    private Instant orderDate;

    private String orderStatus;

    private long amount;
}
