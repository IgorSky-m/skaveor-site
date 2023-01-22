package com.skachko.shop.auth.service.entities.user.service;


import com.skachko.shop.auth.service.entities.user.client.api.IUserFeignClient;
import com.skachko.shop.auth.service.entities.user.dto.CustomUser;
import com.skachko.shop.auth.service.entities.user.dto.UserRequest;
import com.skachko.shop.auth.service.entities.user.service.api.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private final IUserFeignClient client;

    @Transactional(readOnly = true)
    @Override
    public CustomUser getUserByEmail(String email, String password) {
        return client.getByEmailAndPassword(new UserRequest(email, password));
    }

    @Override
    public Boolean isEmailExist(String email) {
        return client.isEmailExist(email)
                .getBody();
    }

    @Transactional
    @Override
    public CustomUser save(CustomUser customUser) {
        return client.create(customUser);
    }
}
