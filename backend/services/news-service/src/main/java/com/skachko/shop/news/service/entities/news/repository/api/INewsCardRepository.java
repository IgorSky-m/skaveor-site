package com.skachko.shop.news.service.entities.news.repository.api;

import com.skachko.shop.news.service.entities.news.dto.NewsCard;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface INewsCardRepository extends JpaRepositoryImplementation<NewsCard, UUID> {
}
