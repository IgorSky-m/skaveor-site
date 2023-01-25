package com.skachko.shop.auth.service.entities.auth.service.api;

import com.skachko.shop.auth.service.entities.auth.dto.AuthHistory;

public interface IAuthHistoryService {
    AuthHistory save(AuthHistory history);
}
