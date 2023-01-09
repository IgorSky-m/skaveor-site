package com.skachko.news.service.entities.deal.dto;

import com.skachko.news.service.entities.deal.api.EDealStatus;
import com.skachko.news.service.entities.deal.api.EDealType;
import com.skachko.news.service.libraries.mvc.api.AEntity;
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
public class Deal extends AEntity<UUID> {

    private EDealType type;
    private EDealStatus status;
    private boolean limitedTimeDealTimer;
    private Date dtFrom;
    private Date dtTo;
    private boolean discountEnabled;
    private BigDecimal discountPercent;

}
