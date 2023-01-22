package com.skachko.account.service.entities.user.service.api;

import com.skachko.account.service.entities.user.api.EUserRole;
import com.skachko.account.service.entities.user.dto.CustomUser;
import com.skachko.account.service.entities.user.dto.UserRequest;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public interface IUserService {
    CustomUser getUserByEmail(String email);
    CustomUser save(CustomUser customUser);

    CustomUser getOne(UUID id);

    CustomUser getByRequest(UserRequest request);


    CustomUser update(UUID id, CustomUser newUser, Date version);
    CustomUser updateRoles(UUID id, Date version, Set<EUserRole> newRoles);

    CustomUser deactivate(UUID id, Date version);
    CustomUser activate(UUID id, Date version);

    Boolean isEmailExist(String email);
}
