package com.skachko.entities.rest.api_case;

import com.skachko.entities.rest.api_case.api.IMyEntity;
import com.skachko.libraries.mvc.api.ICRUDService;
import com.skachko.libraries.search.annotations.ASearchCriteria;
import com.skachko.libraries.search.api.ISearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/my")
public class MyController {

    private final ICRUDService<IMyEntity, UUID> service;


    public MyController(ICRUDService<IMyEntity, UUID> service) {
        this.service = service;
    }


    @GetMapping("/test")
    public List<IMyEntity> testSpec(@ASearchCriteria ISearchCriteria criteria) {
        return service.findAll(criteria);
    }

    @GetMapping("/{id}")
    public IMyEntity getOne(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    public List<IMyEntity> getAll() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        log.error("TEST EP");
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<IMyEntity> create(@RequestBody MyEntity entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public IMyEntity update(@RequestBody MyEntity entity, @PathVariable UUID id) {
        return service.update(id, new Date(), entity);
    }

    @DeleteMapping("/{id}/version/{version}")
    public void delete(@PathVariable UUID id, @PathVariable Date version) {
        service.delete(id, version);
    }
}
