package com.skachko.shop.catalog.service.libraries.mvc.api;


import com.skachko.shop.catalog.service.libraries.filter.ViewConstraints;
import com.skachko.shop.catalog.service.libraries.filter.annotation.ResponseViewLevel;
import com.skachko.shop.catalog.service.libraries.mvc.annotations.ResolveParameters;
import com.skachko.shop.catalog.service.libraries.search.annotations.ASearchCriteria;
import com.skachko.shop.catalog.service.libraries.search.api.ISearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public abstract class ARestController<T, ID> {

    protected final ICRUDService<T, ID> service;

    public ARestController(ICRUDService<T, ID> service) {
        this.service = service;
    }

    @ResponseViewLevel(ViewConstraints.ViewLevel.DETAILED)
    @GetMapping("/{id_key}")
    public T getOne(@ResolveParameters IPathParamContainer<ID> paramContainer){
        return service.getById(paramContainer);
    }

    @ResponseViewLevel(ViewConstraints.ViewLevel.TABLE)
    @GetMapping
    public List<T> getAll(@ASearchCriteria ISearchCriteria criteria){
        return service.findAll(criteria);
    }

    @ResponseViewLevel(ViewConstraints.ViewLevel.DETAILED)
    @PostMapping
    public T create(@RequestBody T t){
        return service.save(t);
    }

    @ResponseViewLevel(ViewConstraints.ViewLevel.TABLE)
    @PostMapping("/all")
    public List<T> createMultiple(@RequestBody Collection<T> all){
        return service.save(all);
    }

    @ResponseViewLevel(ViewConstraints.ViewLevel.DETAILED)
    @PutMapping("/{id_key}/dt_update/{version}")
    public T update(@ResolveParameters IPathParamContainer<ID> paramContainer, @PathVariable Date version, @RequestBody T t) {
        return service.update(paramContainer, version, t);
    }

    @ResponseViewLevel(ViewConstraints.ViewLevel.DETAILED)
    @DeleteMapping("/{id_key}/dt_update/{version}")
    public T deleteById(@ResolveParameters IPathParamContainer<ID> paramContainer, @PathVariable Date version){
        return service.delete(paramContainer, version);
    }

    @ResponseViewLevel(ViewConstraints.ViewLevel.TABLE)
    @GetMapping("/page")
    public Page<T> getPage(
            @ASearchCriteria ISearchCriteria criteria,
            @RequestParam int page,
            @RequestParam int size
            ) {
        return service.findAll(criteria, page, size);
    }

}
