package com.skachko.news.service.libraries.search;

import com.skachko.news.service.libraries.search.api.ISearchPredicate;
import com.skachko.news.service.libraries.search.api.ESort;
import com.skachko.news.service.libraries.search.api.ISearchCriteria;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class SearchCriteria implements ISearchCriteria {

    private ISearchPredicate searchPredicate;
    private Map<String, ESort> sortFields;
}
