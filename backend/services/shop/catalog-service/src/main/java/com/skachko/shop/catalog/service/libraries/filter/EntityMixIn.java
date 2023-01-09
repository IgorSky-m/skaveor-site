package com.skachko.shop.catalog.service.libraries.filter;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * Класс для подмены
 * Необходимо добавить mixIn в Jackson2ObjectMapperBuilder при настроке
 * .mixIn(Object.class, EntityMixIn.class);
 */
@JsonFilter(ViewConstraints.FIELD_VIEW_FILTER_ID)
public class EntityMixIn {
}
