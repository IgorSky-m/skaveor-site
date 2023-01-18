package com.skachko.shop.warehouse.service.entities.order.repository.api;


import com.skachko.shop.warehouse.service.entities.order.dto.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IOrderRepository extends JpaRepository<Order, UUID> {
}
