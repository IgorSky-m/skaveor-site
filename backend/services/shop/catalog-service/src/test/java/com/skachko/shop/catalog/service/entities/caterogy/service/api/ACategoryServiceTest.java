package com.skachko.shop.catalog.service.entities.caterogy.service.api;

import com.skachko.shop.catalog.service.entities.caterogy.dto.Category;
import com.skachko.shop.catalog.service.entities.caterogy.dto.api.ECategoryPrivacyType;
import com.skachko.shop.catalog.service.entities.caterogy.dto.api.ECategoryVisibility;

import java.util.Date;

public abstract class ACategoryServiceTest {

    protected final String ICON_FIELD_VALUE = "http://test.ico";
    protected final String NAME_FIELD_VALUE = "Root";
    protected final String DESCRIPTION_FIELD_VALUE = "RootCategory description";
    protected final String META_FIELD_VALUE = "meta";


    protected Category getTestCategory(Category parent, String prefix, Date...date){
        Category testCategory = getTestCategory(prefix, date);
        testCategory.setParentCategory(parent);
        return testCategory;
    }

    protected Category getTestCategory(Category parent, Date...date){
        Category testCategory = getTestCategory(date);
        testCategory.setParentCategory(parent);
        return testCategory;
    }

    protected Category getTestCategory(Category parent,String prefix,String suffix, Date...date){
        Category testCategory = getTestCategory(prefix, suffix, date);
        testCategory.setParentCategory(parent);
        return testCategory;
    }

    protected Category getTestCategory(String prefix, Date...date){
        Date dtCreate = date.length > 0 ? date[0] : null;
        Category category = new Category();
        category.setIcon(prefix + ICON_FIELD_VALUE);
        category.setName(prefix + "Root");
        category.setDescription(prefix + DESCRIPTION_FIELD_VALUE);
        category.setPrivacy(ECategoryPrivacyType.PUBLIC);
        category.setDtFrom(new Date(1));
        category.setDtTo(new Date(1000000000));
        category.setDtCreate(dtCreate);
        category.setDtUpdate(dtCreate);
        category.setMeta(prefix + META_FIELD_VALUE);
        category.setVisibility(ECategoryVisibility.VISIBLE);

        return category;
    }

    protected Category getTestCategory(String prefix, String suffix, Date...date){

        Date dtCreate = date.length > 0 ? date[0] : null;
        Category category = new Category();
        category.setIcon(prefix + ICON_FIELD_VALUE + suffix);
        category.setName(prefix + NAME_FIELD_VALUE + suffix);
        category.setDescription(prefix + DESCRIPTION_FIELD_VALUE + suffix);
        category.setPrivacy(ECategoryPrivacyType.PUBLIC);
        category.setDtFrom(new Date(1));
        category.setDtTo(new Date(1000000000));
        category.setDtCreate(dtCreate);
        category.setDtUpdate(dtCreate);
        category.setMeta(prefix + META_FIELD_VALUE + suffix);
        category.setVisibility(ECategoryVisibility.VISIBLE);

        return category;
    }


    protected Category getTestCategory(Date...date){
        Date dtCreate = date.length > 0 ? date[0] : null;
            Category category = new Category();
            category.setIcon(ICON_FIELD_VALUE);
            category.setName(NAME_FIELD_VALUE);
            category.setDescription(DESCRIPTION_FIELD_VALUE);
            category.setPrivacy(ECategoryPrivacyType.PUBLIC);
            category.setDtFrom(new Date(1));
            category.setDtTo(new Date(1000000000));
            category.setDtCreate(dtCreate);
            category.setDtUpdate(dtCreate);
            category.setMeta(META_FIELD_VALUE);
            category.setVisibility(ECategoryVisibility.VISIBLE);

        return category;
    }



}
