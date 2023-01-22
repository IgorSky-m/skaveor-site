package com.skachko.shop.account.service.entities.message.email.service;

import com.skachko.shop.account.service.entities.message.dto.CustomUserMessage;
import com.skachko.shop.account.service.entities.message.email.dto.EmailMessageRequest;
import com.skachko.shop.account.service.entities.message.email.repository.api.IEmailMessageRepository;
import com.skachko.shop.account.service.entities.message.email.service.api.IEmailService;
import com.skachko.shop.account.service.exceptions.EntityNotFoundException;
import com.skachko.shop.account.service.exceptions.MsgServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class EmailService implements IEmailService {

    private final IEmailMessageRepository repository;
    private final MessageSource messageSource;

    @Transactional
    @Override
    public CustomUserMessage save(CustomUserMessage message) {
        try {
            return repository.save(message);
        } catch (Exception e) {
            throw new MsgServiceException(messageSource.getMessage("error.crud.create.one", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public List<CustomUserMessage> getByUserId(UUID userId) {
        try {
            return repository.findAllByUserId(userId);
        } catch (Exception e) {
            throw new MsgServiceException(messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public CustomUserMessage getById(UUID id) {
        try {
            return repository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);
        } catch (Exception e) {
            throw new MsgServiceException(messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public List<EmailMessageRequest.FileProps> convert(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return Collections.EMPTY_LIST;
        }

        return Arrays.stream(files).map(e -> {
                    try {
                        return new EmailMessageRequest.FileProps(e.getOriginalFilename(), e.getContentType(), e.getBytes());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
