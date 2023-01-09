package com.skachko.entities.account.dto;

import com.skachko.libraries.mvc.api.IBaseEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account implements IBaseEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Date dtCreate;
    private Date dtUpdate;
    private Date dtDelete;
    private String meta;


    private String login;

    private String password;


}
