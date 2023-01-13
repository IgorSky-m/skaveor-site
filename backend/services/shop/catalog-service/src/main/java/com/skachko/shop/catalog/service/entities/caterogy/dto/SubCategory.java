package com.skachko.shop.catalog.service.entities.caterogy.dto;

import com.skachko.shop.catalog.service.entities.caterogy.dto.api.ECategoryPrivacyType;
import com.skachko.shop.catalog.service.entities.caterogy.dto.api.ECategoryVisibility;
import com.skachko.shop.catalog.service.libraries.mvc.api.AEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class SubCategory extends AEntity {

    private String icon;
    private String name;
    private String description;
    private ECategoryVisibility visibility;
    private ECategoryPrivacyType privacy;
    private Date dtFrom;
    private Date dtTo;

    public SubCategory(Category category) {
        setId(category.getId());
        setDtCreate(category.getDtCreate());
        setDtUpdate(category.getDtUpdate());
        setDtDelete(category.getDtDelete());
        setMeta(category.getMeta());
        setIcon(category.getIcon());
        setName(category.getName());
        setDescription(category.getDescription());
        setVisibility(category.getVisibility());
        setPrivacy(category.getPrivacy());
        setDtFrom(category.getDtFrom());
        setDtTo(category.getDtTo());
    }



}
