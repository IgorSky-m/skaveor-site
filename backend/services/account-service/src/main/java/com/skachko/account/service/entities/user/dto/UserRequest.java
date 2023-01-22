package com.skachko.account.service.entities.user.dto;


import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String password;
}
