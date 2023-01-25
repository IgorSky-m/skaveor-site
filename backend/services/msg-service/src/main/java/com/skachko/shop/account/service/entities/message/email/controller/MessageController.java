package com.skachko.shop.account.service.entities.message.email.controller;

import com.skachko.shop.account.service.entities.message.api.IMessageService;
import com.skachko.shop.account.service.entities.message.dto.CustomUserMessage;
import com.skachko.shop.account.service.entities.message.dto.api.EMessageType;
import com.skachko.shop.account.service.entities.message.email.dto.EmailMessageRequest;
import com.skachko.shop.account.service.entities.message.email.service.api.IEmailService;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Arrays;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/messages/email")
@RequiredArgsConstructor
public class MessageController {

    private final IMessageService<EmailMessageRequest> service;
    private final IEmailService emailService;

    @PostMapping(value = "/send")
    public void send(
            @RequestParam String receiver,
            @RequestParam String text,
            @RequestParam String subject,
            @RequestParam(required = false) MultipartFile[] files,
            @RequestHeader Map<String, String> headers
    ) {
        service.sendAndSave(
                EmailMessageRequest.builder()
                        .messageType(EMessageType.EMAIL)
                        .text(text)
                        .subject(subject)
                        .receiver(receiver)
                        .files(emailService.convert(Arrays.array(files)))
                        .build(),
                headers
        );
    }

    @GetMapping("/{id}")
    public CustomUserMessage getOne(@PathVariable UUID id) {
        return service.getMessage(id);
    }

    @GetMapping("/user/{userId}")
    public List<CustomUserMessage> getAllByUserId(@PathVariable UUID userId) {
        return service.getListByUserId(userId);
    }
}
