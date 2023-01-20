package com.skachko.shop.news.service.entities.advantages.repository.api;

import com.skachko.shop.news.service.entities.advantages.dto.Advantage;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IAdvantageRepository extends JpaRepositoryImplementation<Advantage, UUID> {
}
