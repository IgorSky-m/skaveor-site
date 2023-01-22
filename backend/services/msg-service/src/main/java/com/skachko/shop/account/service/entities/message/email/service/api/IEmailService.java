package com.skachko.shop.account.service.entities.message.email.service.api;

import com.skachko.shop.account.service.entities.message.dto.CustomUserMessage;
import com.skachko.shop.account.service.entities.message.email.dto.EmailMessageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IEmailService {

    CustomUserMessage save(CustomUserMessage message);

    List<CustomUserMessage> getByUserId(UUID userId);

    CustomUserMessage getById(UUID id);


    List<EmailMessageRequest.FileProps> convert(MultipartFile[] files);

}
