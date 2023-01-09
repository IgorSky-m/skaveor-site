package com.skachko.news.service.entities.deal.service.api;

import com.skachko.news.service.entities.deal.dto.Deal;
import com.skachko.news.service.libraries.mvc.api.ICRUDService;

import java.util.UUID;

public interface IDealService extends ICRUDService<Deal, UUID> {
}
