package com.skachko.shop.catalog.service.entities.deal.dto;

import com.skachko.shop.catalog.service.entities.deal.api.EDealStatus;
import com.skachko.shop.catalog.service.entities.deal.api.EDealType;
import com.skachko.shop.catalog.service.entities.item.dto.Item;
import com.skachko.shop.catalog.service.libraries.mvc.api.AEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Deal extends AEntity {

    private EDealType type;
    private EDealStatus status;
    private boolean limitedTimeDealTimer;
    private Date dtFrom;
    private Date dtTo;
    private boolean discountEnabled;
    private BigDecimal discountPercent;

}
