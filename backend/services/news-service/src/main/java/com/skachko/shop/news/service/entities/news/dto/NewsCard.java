package com.skachko.shop.news.service.entities.news.dto;


import com.skachko.shop.news.service.entities.images.dto.Image;
import com.skachko.shop.news.service.libraries.mvc.api.AEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsCard extends AEntity {

    private String title;

    private String summary;

    @Column(columnDefinition="text")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Image.class)
    private List<Image> images;

    private String redirectToCategory;
    private String redirectToItem;
    private String redirectToDeal;
}
