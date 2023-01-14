package com.skachko.shop.payment.service.libraries.search;

import com.skachko.shop.payment.service.libraries.search.api.ISearchPredicate;
import com.skachko.shop.payment.service.libraries.search.api.ISearchCriteria;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Map;

@Data
@NoArgsConstructor
public class SearchCriteria implements ISearchCriteria {

    private ISearchPredicate searchPredicate;
    private Map<String, Sort.Direction> sortFields;

    public SearchCriteria(ISearchPredicate searchPredicate) {
        this.searchPredicate = searchPredicate;
    }
}
