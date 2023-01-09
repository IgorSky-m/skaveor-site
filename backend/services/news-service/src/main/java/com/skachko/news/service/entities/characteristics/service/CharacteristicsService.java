package com.skachko.news.service.entities.characteristics.service;

import com.skachko.news.service.entities.characteristics.dto.Characteristics;
import com.skachko.news.service.entities.characteristics.service.api.ICharacteristicsService;
import com.skachko.news.service.libraries.mvc.api.ABaseCRUDService;
import com.skachko.news.service.libraries.search.api.ACriteriaToSpecificationConverter;
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
            MessageSource messageSource) {
        super(delegate, ACriteriaToSpecificationConverter.of(Characteristics.class), messageSource);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}
