package com.skachko.shop.news.service.entities.advantages.controller;

import com.skachko.shop.news.service.entities.advantages.dto.Advantage;
import com.skachko.shop.news.service.entities.advantages.service.api.IAdvantageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/advantages")
@RequiredArgsConstructor
public class AdvantageController {

    private final IAdvantageService service;

    @GetMapping
    public List<Advantage> getAll() {
        return service.getAll();
    }


    @PostMapping
    public List<Advantage> saveAll(@RequestBody List<Advantage> advantages){
        return service.saveAll(advantages);
    }
}
