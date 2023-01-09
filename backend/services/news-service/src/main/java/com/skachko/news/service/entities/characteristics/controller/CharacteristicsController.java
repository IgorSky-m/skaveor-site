package com.skachko.news.service.entities.characteristics.controller;

import com.skachko.news.service.entities.characteristics.dto.Characteristics;
import com.skachko.news.service.entities.characteristics.service.api.ICharacteristicsService;
import com.skachko.news.service.libraries.mvc.api.ARestController;
import com.skachko.news.service.libraries.mvc.api.IPathParamContainer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/items/{item_id}/characteristics")
public class CharacteristicsController extends ARestController<Characteristics, UUID> {

    public CharacteristicsController(ICharacteristicsService service) {
        super(service);
    }

    @Override
    public Characteristics create(Characteristics characteristics) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Characteristics> createMultiple(Collection<Characteristics> all) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Characteristics update(IPathParamContainer<UUID> paramContainer, Date version, Characteristics characteristics) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Characteristics deleteById(IPathParamContainer<UUID> paramContainer, Date version) {
        throw new UnsupportedOperationException();
    }
}
