package com.skachko.shop.order.service.entities.order.repository.api;

import com.skachko.shop.order.service.entities.order.dto.OrderProductDetails;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IOrderProductDetailsRepository extends JpaRepositoryImplementation<OrderProductDetails, UUID> {
}
