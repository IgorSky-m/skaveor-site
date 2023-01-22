package com.skachko.shop.account.service.entities.message.api;

import com.skachko.shop.account.service.entities.message.dto.CustomUserMessage;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IMessageService<T> {

    void sendAndSave(T request, Map<String, String> readers);

    List<CustomUserMessage> getListByUserId(UUID userId);

    CustomUserMessage getMessage(UUID id);
}
