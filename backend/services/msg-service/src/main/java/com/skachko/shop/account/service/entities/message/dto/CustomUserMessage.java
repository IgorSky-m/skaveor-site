package com.skachko.shop.account.service.entities.message.dto;

import com.skachko.shop.account.service.entities.message.dto.api.EMessageStatus;
import com.skachko.shop.account.service.entities.message.dto.api.EMessageType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    private Date dtCreate;

    private UUID userId;

    @Enumerated(EnumType.STRING)
    private EMessageType messageType;

    @Enumerated(EnumType.STRING)
    private EMessageStatus status;

    private String subject;

    @Column(columnDefinition="text")
    private String text;

    private String sender;

    private String receiver;
}
