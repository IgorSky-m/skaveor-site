package com.skachko.libraries.mvc.api;


import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public T update(@PathVariable ID id, @RequestBody T t) {
        return service.update(id, t);
    }

    @DeleteMapping("/{id}")
    public T deleteById(@PathVariable ID id){
        return service.deleteById(id);
    }

}
