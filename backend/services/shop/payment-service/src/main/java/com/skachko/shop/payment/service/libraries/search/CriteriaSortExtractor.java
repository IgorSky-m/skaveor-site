package com.skachko.shop.payment.service.libraries.search;

import com.skachko.shop.payment.service.libraries.search.api.ICriteriaSortExtractor;
import com.skachko.shop.payment.service.libraries.search.api.ISearchCriteria;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CriteriaSortExtractor implements ICriteriaSortExtractor {

    public CriteriaSortExtractor(){}


    public Optional<Sort> getSort(ISearchCriteria criteria){
        Map<String, Sort.Direction> sortFields = criteria.getSortFields();
        Sort sort = null;
        if (sortFields != null && !sortFields.isEmpty()) {
            sort = Sort.by(sortFields.entrySet().stream()
                    .map(this::getOrder)
                    .collect(Collectors.toCollection(ArrayList::new)));
        }

        return Optional.ofNullable(sort);
    }

    private Sort.Order getOrder(Map.Entry<String, Sort.Direction> entry) {
        return switch (entry.getValue()) {
            case ASC -> Sort.Order.asc(entry.getKey());
            case DESC -> Sort.Order.desc(entry.getKey());
        };
    }




}
