package com.skachko.shop.auth.service.entities.user.service;


import com.skachko.shop.auth.service.entities.user.dto.CustomUser;
import com.skachko.shop.auth.service.entities.user.repository.api.ICustomUserRepository;
import com.skachko.shop.auth.service.entities.user.service.api.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private final ICustomUserRepository repository;

    @Transactional(readOnly = true)
    @Override
    public CustomUser getUserByEmail(String email) {
        return repository.findOneByEmail(email).orElse(null);
    }

    @Transactional
    @Override
    public CustomUser save(CustomUser customUser) {
        return repository.save(customUser);
    }
}
