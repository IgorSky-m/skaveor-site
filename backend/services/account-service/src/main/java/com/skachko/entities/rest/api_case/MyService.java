package com.skachko.entities.rest.api_case;

import com.skachko.entities.rest.api_case.api.IMyEntity;
import com.skachko.entities.rest.api_case.api.IMyEntityRepository;
import com.skachko.libraries.mvc.api.AProjectionCRUDService;
import com.skachko.libraries.search.api.ACriteriaToSpecificationConverter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Transactional(readOnly = true)
@Service
public class MyService extends AProjectionCRUDService<IMyEntity, MyEntity, UUID> {

    private static final Logger logger = LoggerFactory.getLogger(MyService.class);
    public MyService(
            IMyEntityRepository repository,
            MessageSource messageSource
    ) {
        super(repository, MyEntity::new, ACriteriaToSpecificationConverter.of(MyEntity.class), messageSource);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

}
