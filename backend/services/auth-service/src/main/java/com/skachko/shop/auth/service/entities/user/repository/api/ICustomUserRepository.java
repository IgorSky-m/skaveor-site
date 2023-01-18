package com.skachko.shop.auth.service.entities.user.repository.api;

import com.skachko.shop.auth.service.entities.user.dto.CustomUser;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICustomUserRepository extends JpaRepositoryImplementation<CustomUser, UUID> {
    Optional<CustomUser> findOneByEmail(String email);
}
