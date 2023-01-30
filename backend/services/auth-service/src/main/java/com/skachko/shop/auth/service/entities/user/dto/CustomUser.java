package com.skachko.shop.auth.service.entities.user.dto;

import com.skachko.shop.auth.service.entities.user.api.EUserRole;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.*;

import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUser {

    private UUID id;

    private Long dtCreate;

    private Long dtUpdate;

    private Long dtDelete;

    private String meta;

    private String email;
    private String name;
    private String password;



    @ElementCollection(fetch = FetchType.EAGER, targetClass = EUserRole.class)
    private Set<EUserRole> roles;
}
