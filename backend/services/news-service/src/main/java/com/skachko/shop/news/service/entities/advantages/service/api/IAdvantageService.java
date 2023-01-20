package com.skachko.shop.news.service.entities.advantages.service.api;

import com.skachko.shop.news.service.entities.advantages.dto.Advantage;

import java.util.List;

public interface IAdvantageService {
    List<Advantage> getAll();


    List<Advantage> saveAll(List<Advantage> advantages);
}
