package com.skachko.shop.auth.service.entities.auth.repository.api;

import com.skachko.shop.auth.service.entities.auth.dto.AuthHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAuthHistoryRepository extends JpaRepository<AuthHistory, UUID> {
}
