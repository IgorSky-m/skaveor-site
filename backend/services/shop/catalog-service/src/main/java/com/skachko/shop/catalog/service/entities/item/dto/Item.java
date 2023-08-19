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

import java.util.Date;
import java.util.List;


/**
 * Item DTO
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "items")
public class Item extends AEntity {

    private String title;
    private String summary;
    private String type;
    private EItemVisibility visibility;
    private EItemPrivacy privacy;
    private Long price;
    private Date dtFrom;
    private Date dtTo;
    private String titlePicture;


    @ManyToMany(targetEntity = Category.class, cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
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
