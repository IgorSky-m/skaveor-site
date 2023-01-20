package com.skachko.shop.news.service.entities.news.service.api;


import com.skachko.shop.news.service.entities.news.dto.NewsCard;
import com.skachko.shop.news.service.libraries.mvc.api.ICRUDService;

import java.util.UUID;

public interface INewsCardService extends ICRUDService<NewsCard, UUID> {
}
