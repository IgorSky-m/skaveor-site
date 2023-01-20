package com.skachko.shop.news.service.entities.advantages.dto;

import com.skachko.shop.news.service.libraries.mvc.api.AEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Advantage extends AEntity {

    private String img;
    private String alt;
    private String description;
}
