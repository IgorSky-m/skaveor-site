package com.skachko.shop.news.service.entities.news.controller;

import com.skachko.shop.news.service.entities.news.dto.NewsCard;
import com.skachko.shop.news.service.entities.news.service.api.INewsCardService;
import com.skachko.shop.news.service.libraries.mvc.api.ARestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/news")
public class NewsController extends ARestController<NewsCard, UUID> {

    public NewsController(INewsCardService service) {
        super(service);
    }




}
