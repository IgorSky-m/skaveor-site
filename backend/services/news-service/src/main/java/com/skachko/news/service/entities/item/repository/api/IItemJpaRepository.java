package com.skachko.news.service.entities.item.repository.api;

import com.skachko.news.service.entities.item.dto.Item;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IItemJpaRepository extends JpaRepositoryImplementation<Item, UUID> {
}
