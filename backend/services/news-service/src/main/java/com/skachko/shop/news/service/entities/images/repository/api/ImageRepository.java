package com.skachko.shop.news.service.entities.images.repository.api;

import com.skachko.shop.news.service.entities.images.dto.Image;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepositoryImplementation<Image, UUID> {
}
