package com.skachko.news.service.entities.characteristics.dto;


import com.skachko.news.service.libraries.mvc.api.AEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Characteristics extends AEntity<UUID> {
    private String description;
}
