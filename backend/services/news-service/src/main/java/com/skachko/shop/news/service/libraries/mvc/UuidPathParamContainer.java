package com.skachko.shop.news.service.libraries.mvc;

import com.skachko.shop.news.service.libraries.mvc.api.APathParamContainer;
import com.skachko.shop.news.service.libraries.mvc.api.IUuidPathParamContainer;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class UuidPathParamContainer extends APathParamContainer<UUID> implements IUuidPathParamContainer {

    public UuidPathParamContainer() {
        super(UUID::fromString);
    }

    public UuidPathParamContainer(Map<String, UUID> parameters) {
        super(parameters, UUID::fromString);
    }

    public UuidPathParamContainer(Map<String, UUID> parameters, Function<String, UUID> convertFunc) {
        super(parameters, convertFunc);
    }
}
