package com.skachko.shop.catalog.service.entities.item.dto;

import com.skachko.shop.catalog.service.entities.item.api.EItemPrivacy;
import com.skachko.shop.catalog.service.entities.item.api.EItemVisibility;
import com.skachko.shop.catalog.service.libraries.mvc.api.AEntity;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import javax.annotation.processing.Generated;
import java.util.Date;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Item.class)
public class Item_ {

    public static volatile SingularAttribute<Item, UUID> id;
    public static volatile SingularAttribute<Item, Date> dtCreate;
    public static volatile SingularAttribute<Item, Date> dtUpdate;
    public static volatile SingularAttribute<Item, Date> dtDelete;
    public static volatile SingularAttribute<Item, String> meta;


    public static volatile SingularAttribute<Item, String> title;
    public static volatile SingularAttribute<Item, String> summary;
    public static volatile SingularAttribute<Item, String> type;
    public static volatile SingularAttribute<Item, EItemVisibility> visibility;
    public static volatile SingularAttribute<Item, EItemPrivacy> privacy;
    public static volatile SingularAttribute<Item, Long> price;
    public static volatile SingularAttribute<Item, Date> dtFrom;
    public static volatile SingularAttribute<Item, Date> dtTo;
    public static volatile SingularAttribute<Item, String> titlePicture;

    public static final String ID = AEntity.ID;
    public static final String DT_CREATE = AEntity.DT_CREATE;
    public static final String DT_UPDATE = AEntity.DT_UPDATE;
    public static final String DT_DELETE = AEntity.DT_DELETE;
    public static final String META = AEntity.META;

    public static final String TITLE = "title";
    public static final String SUMMARY = "summary";
    public static final String TYPE = "type";
    public static final String VISIBILITY = "visibility";
    public static final String PRIVACY = "privacy";
    public static final String PRICE = "price";
    public static final String DT_FROM = "dtFrom";
    public static final String DT_TO = "dtTo";
    public static final String TITLE_PICTURE = "titlePicture";


}
