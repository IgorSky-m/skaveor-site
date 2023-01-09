package com.skachko.libraries.mvc.api;


import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

public abstract class AbstractBaseController<T, ID> {

    private final ICRUDService<T, ID> service;

    public AbstractBaseController(ICRUDService<T, ID> service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public T getOne(@PathVariable ID id){
        return service.getById(id);
    }

    @GetMapping
    public List<T> getAll(){
        return service.findAll();
    }


    @PostMapping
    public T create(@RequestBody T t){
        return service.save(t);
    }

    @PutMapping("/{id}/dt_update/{version}")
    public T update(@PathVariable ID id, @PathVariable Date version, @RequestBody T t) {
        return service.update(id, version, t);
    }

    @DeleteMapping("/{id}/dt_update/{version}")
    public T deleteById(@PathVariable ID id, @PathVariable Date version){
        return service.delete(id, version);
    }

}
