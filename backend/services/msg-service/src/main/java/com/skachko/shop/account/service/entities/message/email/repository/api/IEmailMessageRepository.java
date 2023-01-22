package com.skachko.shop.account.service.entities.message.email.repository.api;

import com.skachko.shop.account.service.entities.message.dto.CustomUserMessage;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;
import java.util.UUID;

public interface IEmailMessageRepository extends JpaRepositoryImplementation<CustomUserMessage, UUID> {
    List<CustomUserMessage> findAllByUserId(UUID userId);
}
