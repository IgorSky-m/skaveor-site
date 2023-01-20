package com.skachko.shop.news.service.entities.images.dto;

import com.skachko.shop.news.service.entities.images.api.EImageType;
import com.skachko.shop.news.service.libraries.mvc.api.AEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//TODO Image storage
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Image extends AEntity {

    private String src;
    private String type;
    private String alt;
    private EImageType imageType;

}
