package com.skachko.entities.rest.api_case;

import com.skachko.entities.rest.api_case.api.IMyEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyEntity implements IMyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String description;

    private int number;

    private Date date;

    public MyEntity(IMyEntity other){
        this.id = other.getId();
        this.name = other.getName();
        this.description = other.getDescription();
        this.number = other.getNumber();
        this.date = other.getDate();
    }
}
