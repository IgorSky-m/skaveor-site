package com.skachko.shop.catalog.service.entities.deal.service;

import com.skachko.shop.catalog.service.entities.deal.api.EDealType;
import com.skachko.shop.catalog.service.entities.deal.dto.Deal;
import com.skachko.shop.catalog.service.entities.deal.service.api.IDealService;
import com.skachko.shop.catalog.service.libraries.mvc.api.ABaseCRUDService;
import com.skachko.shop.catalog.service.libraries.search.api.ICriteriaSortExtractor;
import com.skachko.shop.catalog.service.libraries.search.converter.api.ACriteriaToSpecificationConverter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DealService extends ABaseCRUDService<Deal, UUID> implements IDealService {

    public DealService(
            JpaRepositoryImplementation<Deal, UUID> repository,
            MessageSource messageSource,
            ICriteriaSortExtractor extractor
    ) {
        super(repository, ACriteriaToSpecificationConverter.of(Deal.class), messageSource, extractor);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    //TODO replace enum to return list of current deals
    @Override
    public List<String> geDealTypes() {
        return Arrays.stream(EDealType.values())
                .map(Enum::name)
                .toList();
    }
}
