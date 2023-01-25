package com.skachko.shop.auth.service.entities.auth.dto;

import com.skachko.shop.auth.service.entities.auth.dto.api.EAuthAction;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AuthHistory {

    @Id
    private UUID id;
    private Date dtAction;

    private UUID userId;

    @Enumerated(EnumType.STRING)
    private EAuthAction action;
}
