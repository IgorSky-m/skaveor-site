package com.skachko.shop.auth.service.entities.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skachko.shop.auth.service.entities.user.api.EUserRole;
import com.skachko.shop.auth.service.libraries.mvc.api.AEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUser extends AEntity {

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = EUserRole.class)
    private Set<EUserRole> roles;
}
