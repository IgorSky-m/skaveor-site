package com.skachko.shop.catalog.service.entities.caterogy.repository;

import com.skachko.shop.catalog.service.entities.caterogy.dto.Category;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICategoryRepository extends JpaRepositoryImplementation<Category, UUID> {


}
