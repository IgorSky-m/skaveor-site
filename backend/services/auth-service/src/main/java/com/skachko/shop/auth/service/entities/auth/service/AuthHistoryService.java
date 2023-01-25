package com.skachko.shop.auth.service.entities.auth.service;

import com.skachko.shop.auth.service.entities.auth.dto.AuthHistory;
import com.skachko.shop.auth.service.entities.auth.repository.api.IAuthHistoryRepository;
import com.skachko.shop.auth.service.entities.auth.service.api.IAuthHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthHistoryService implements IAuthHistoryService {

    private final IAuthHistoryRepository repository;

    @Transactional
    @Override
    public AuthHistory save(AuthHistory history) {
        return repository.save(history);
    }
}
