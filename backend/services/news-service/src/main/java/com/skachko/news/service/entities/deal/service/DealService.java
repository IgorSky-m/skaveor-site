package com.skachko.news.service.entities.deal.service;

import com.skachko.news.service.entities.deal.dto.Deal;
import com.skachko.news.service.entities.deal.service.api.IDealService;
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
public class DealService extends ABaseCRUDService<Deal, UUID> implements IDealService {

    public DealService(
            JpaRepositoryImplementation<Deal, UUID> repository,
            MessageSource messageSource
    ) {
        super(repository, ACriteriaToSpecificationConverter.of(Deal.class), messageSource);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}
