package com.skachko.news.service.entities.item.dto;

import com.skachko.news.service.entities.characteristics.dto.Characteristics;
import com.skachko.news.service.entities.deal.dto.Deal;
import com.skachko.news.service.entities.item.api.EItemStatus;
import com.skachko.news.service.libraries.filter.ViewConstraints;
import com.skachko.news.service.libraries.filter.annotation.FieldViewLevel;
import com.skachko.news.service.libraries.mvc.api.AEntity;
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
public class Item extends AEntity<UUID> {

    private String title;
    private String summary;
    private String category;
    private String type;
    private EItemStatus status;
    private BigDecimal price;
    private Date dtFrom;
    private Date dtTo;
    private String titlePicture;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ElementCollection(fetch = FetchType.LAZY, targetClass = Deal.class)
    private List<Deal> deals;

    @FieldViewLevel(viewLevel = ViewConstraints.ViewLevel.DETAILED)
    @ElementCollection(fetch = FetchType.LAZY, targetClass = String.class)
    private List<String> pictures;

    @FieldViewLevel(viewLevel = ViewConstraints.ViewLevel.DETAILED)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Characteristics characteristics;

}
