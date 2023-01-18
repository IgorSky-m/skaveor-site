package com.skachko.shop.order.service.entities.order.repository.api;


import com.skachko.shop.order.service.entities.order.api.OrderStatus;
import com.skachko.shop.order.service.entities.order.dto.CustomOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IOrderRepository extends JpaRepository<CustomOrder, UUID> {

    @Modifying
    @Query("update CustomOrder ord set ord.status = :status where ord.id = :id")
    void updateStatusById(@Param("status") OrderStatus status, @Param("id") UUID id);
}
