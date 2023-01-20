package com.skachko.shop.order.service.entities.catalog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class Item {

    private UUID id;
    private String title;
    private String type;
    private String titlePicture;
    private Long price;
}
