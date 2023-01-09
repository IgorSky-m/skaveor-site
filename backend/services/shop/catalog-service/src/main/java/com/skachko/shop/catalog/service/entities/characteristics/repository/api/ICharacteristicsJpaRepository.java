package com.skachko.shop.catalog.service.entities.characteristics.repository.api;

import com.skachko.shop.catalog.service.entities.characteristics.dto.Characteristics;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICharacteristicsJpaRepository extends JpaRepositoryImplementation<Characteristics, UUID> {
}
