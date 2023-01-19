package com.skachko.shop.auth.service.entities.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRegisterRequest {
    private String email;
    private String password;
    private String name;
}
