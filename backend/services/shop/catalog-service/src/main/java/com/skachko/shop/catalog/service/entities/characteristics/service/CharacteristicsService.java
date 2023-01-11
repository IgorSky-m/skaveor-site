package com.skachko.shop.catalog.service.entities.characteristics.service;

import com.skachko.shop.catalog.service.entities.characteristics.dto.Characteristics;
import com.skachko.shop.catalog.service.entities.characteristics.service.api.ICharacteristicsService;
import com.skachko.shop.catalog.service.libraries.mvc.api.ABaseCRUDService;
import com.skachko.shop.catalog.service.libraries.search.api.ACriteriaToSpecificationConverter;
import com.skachko.shop.catalog.service.libraries.search.api.ICriteriaSortExtractor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CharacteristicsService extends ABaseCRUDService<Characteristics, UUID> implements ICharacteristicsService {

    public CharacteristicsService(
            JpaRepositoryImplementation<Characteristics, UUID> delegate,
            MessageSource messageSource,
            ICriteriaSortExtractor extractor) {
        super(delegate, ACriteriaToSpecificationConverter.of(Characteristics.class), messageSource, extractor);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}
