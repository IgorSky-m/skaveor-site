package com.skachko.shop.auth.service.entities.auth.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ValidateTokenResult {

    private EValidateTokenResult result;
    private String message;

    private TokenPayload payload;


    @Getter
    @Setter
    @Builder
    public static class TokenPayload {
        private UUID id;
        private Long version;
        private List<String> roles;
        private String name;
    }

    public enum EValidateTokenResult {
        SUCCESS,
        FAIL
    }
}
