package com.skachko.shop.catalog.service.entities.caterogy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skachko.shop.catalog.service.entities.caterogy.dto.api.ECategoryPrivacyType;
import com.skachko.shop.catalog.service.entities.caterogy.dto.api.ECategoryVisibility;
import com.skachko.shop.catalog.service.libraries.filter.ViewConstraints;
import com.skachko.shop.catalog.service.libraries.filter.annotation.FieldViewLevel;
import com.skachko.shop.catalog.service.libraries.mvc.api.AEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Category of items in shop
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category extends AEntity<UUID> {

    private String icon;
    private String name;
    private String description;
    private ECategoryVisibility visibility;
    private ECategoryPrivacyType privacy;
    private Date dtFrom;
    private Date dtTo;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Category parentCategory;

    @FieldViewLevel(viewLevel = ViewConstraints.ViewLevel.DETAILED)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Transient
    private List<Category> subCategories;
}


//TODO List of user which allow to see this private category