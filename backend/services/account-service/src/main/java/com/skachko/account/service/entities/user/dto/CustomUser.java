package com.skachko.account.service.entities.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skachko.account.service.entities.user.api.EUserRole;
import com.skachko.account.service.libraries.mvc.api.AEntity;
import jakarta.persistence.*;
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
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;



    @ElementCollection(fetch = FetchType.EAGER, targetClass = EUserRole.class)
    @Enumerated(EnumType.STRING)
    private Set<EUserRole> roles;
}
