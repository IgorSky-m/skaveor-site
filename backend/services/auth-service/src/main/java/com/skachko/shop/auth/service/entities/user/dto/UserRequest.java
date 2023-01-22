package com.skachko.shop.auth.service.entities.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {
    private String email;
    private String password;
}
