package com.skachko.shop.catalog.service.entities.item.dto;

import com.skachko.shop.catalog.service.entities.caterogy.dto.Category;
import com.skachko.shop.catalog.service.entities.characteristics.dto.Characteristics;
import com.skachko.shop.catalog.service.entities.deal.dto.Deal;
import com.skachko.shop.catalog.service.entities.item.api.EItemPrivacy;
import com.skachko.shop.catalog.service.entities.item.api.EItemVisibility;
import com.skachko.shop.catalog.service.libraries.filter.ViewConstraints;
import com.skachko.shop.catalog.service.libraries.filter.annotation.FieldViewLevel;
import com.skachko.shop.catalog.service.libraries.mvc.api.AEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Item DTO
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item extends AEntity {

    private String title;
    private String summary;
    private String type;
    private EItemVisibility visibility;
    private EItemPrivacy privacy;
    private BigDecimal price;
    private Date dtFrom;
    private Date dtTo;
    private String titlePicture;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Category.class)
    private List<Category> categories;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = Deal.class)
    private List<Deal> deals;

    @FieldViewLevel(viewLevel = ViewConstraints.ViewLevel.DETAILED)
    @ElementCollection(fetch = FetchType.LAZY, targetClass = String.class)
    private List<String> pictures;

    @FieldViewLevel(viewLevel = ViewConstraints.ViewLevel.DETAILED)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Characteristics characteristics;

}
