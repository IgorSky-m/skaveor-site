package com.skachko.shop.catalog.service.entities.deal.repository;

import com.skachko.shop.catalog.service.entities.deal.dto.Deal;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IDealRepository extends JpaRepositoryImplementation<Deal, UUID> {
}
