package com.skachko.shop.news.service.entities.advantages.service;

import com.skachko.shop.news.service.entities.advantages.dto.Advantage;
import com.skachko.shop.news.service.entities.advantages.repository.api.IAdvantageRepository;
import com.skachko.shop.news.service.entities.advantages.service.api.IAdvantageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvantageServiceImpl implements IAdvantageService {

    private final IAdvantageRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Advantage> getAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public List<Advantage> saveAll(List<Advantage> advantages) {
        //todo validation before create
        return repository.saveAll(advantages);
    }
}
