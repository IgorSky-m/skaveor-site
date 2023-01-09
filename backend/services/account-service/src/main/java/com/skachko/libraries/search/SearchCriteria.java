package com.skachko.libraries.search;

import com.skachko.libraries.search.api.ESort;
import com.skachko.libraries.search.api.ISearchCriteria;
import com.skachko.libraries.search.api.ISearchPredicate;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class SearchCriteria implements ISearchCriteria {

    private ISearchPredicate searchPredicate;
    private Map<String, ESort> sortFields;
}
