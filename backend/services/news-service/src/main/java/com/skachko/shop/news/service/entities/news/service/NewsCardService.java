package com.skachko.shop.news.service.entities.news.service;

import com.skachko.shop.news.service.entities.images.dto.Image;
import com.skachko.shop.news.service.entities.images.repository.api.ImageRepository;
import com.skachko.shop.news.service.entities.news.dto.NewsCard;
import com.skachko.shop.news.service.entities.news.repository.api.INewsCardRepository;
import com.skachko.shop.news.service.entities.news.service.api.INewsCardService;
import com.skachko.shop.news.service.libraries.mvc.api.ABaseCRUDService;
import com.skachko.shop.news.service.libraries.search.api.ICriteriaSortExtractor;
import com.skachko.shop.news.service.libraries.search.converter.api.ACriteriaToSpecificationConverter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class NewsCardService extends ABaseCRUDService<NewsCard, UUID> implements INewsCardService {

    private final  ImageRepository imageRepository;

    public NewsCardService(
            INewsCardRepository repository,
            MessageSource messageSource,
            ICriteriaSortExtractor criteriaSortExtractor,
            ImageRepository imageRepository
    ) {
        super(repository, ACriteriaToSpecificationConverter.of(NewsCard.class), messageSource, criteriaSortExtractor);
        this.imageRepository = imageRepository;
    }


    @Transactional
    @Override
    public NewsCard save(NewsCard newsCard, Date date) {
        if (newsCard.getImages() != null && !newsCard.getImages().isEmpty()){
            imageRepository.saveAll(newsCard.getImages());
        }
        return super.save(newsCard, date);
    }

    @Transactional
    @Override
    public List<NewsCard> save(Collection<NewsCard> list, Date date) {

        List<Image> images = list.stream().flatMap(e -> e.getImages().stream()).toList();
        imageRepository.saveAll(images);

        return super.save(list, date);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}
