package com.skachko.shop.catalog.service.entities.caterogy.service;

import com.skachko.shop.catalog.service.entities.caterogy.dto.Category;
import com.skachko.shop.catalog.service.entities.caterogy.repository.ICategoryRepository;
import com.skachko.shop.catalog.service.entities.caterogy.service.api.ACategoryServiceTest;
import com.skachko.shop.catalog.service.entities.caterogy.service.api.ICategoryService;
import com.skachko.shop.catalog.service.libraries.mvc.api.AEntity;
import com.skachko.shop.catalog.service.libraries.search.SearchCriteria;
import com.skachko.shop.catalog.service.libraries.search.SearchExpression;
import com.skachko.shop.catalog.service.libraries.search.SearchPredicate;
import com.skachko.shop.catalog.service.libraries.search.api.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableTransactionManagement
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class CategoryServiceTest extends ACategoryServiceTest {

    private List<Category> testCategories;


    private Category parent;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ICategoryRepository repository;

    @BeforeAll
    public void setupAll() {
        parent = repository.save(getTestCategory());
        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            categories.add(getTestCategory(parent, Integer.toString(i)));
        }
        repository.saveAll(categories);
        testCategories = new ArrayList<>(categories);

    }


    @BeforeEach
    public void setup() {
        repository.save(parent);
        repository.saveAll(new ArrayList<>(testCategories));
    }

    @AfterEach
    public void reset() {
        repository.deleteAll();
    }


    @Test
    void findOne() {
        SearchCriteria criteria = new SearchCriteria();
        SearchPredicate predicate = new SearchPredicate();
        predicate.setConditionOperator(EPredicateOperator.AND);

        List<ISearchExpression> expressions = new ArrayList<>();
        expressions.add(ISearchExpression.of(AEntity.ID, EComparisonOperator.EQUAL, parent.getId()));
        predicate.setSearchExpressions(expressions);



        Optional<Category> optional = categoryService.findOne(criteria);
        assertTrue(optional.isPresent());

        Category category = optional.get();
        assertEquals(category.getId(), parent.getId());
        assertEquals(category.getDtCreate(), parent.getDtCreate());
        assertEquals(category.getDtUpdate(), parent.getDtUpdate());
        assertEquals(category.getDtDelete(), parent.getDtDelete());
        assertEquals(category.getMeta(), parent.getMeta());
        assertEquals(category.getParentCategory(), parent.getParentCategory());
        assertEquals(category.getDescription(), parent.getDescription());
        assertEquals(category.getIcon(), parent.getIcon());
        assertEquals(category.getDtFrom().getTime(), parent.getDtFrom().getTime());
        assertEquals(category.getDtTo().getTime(), parent.getDtTo().getTime());
        assertEquals(category.getPrivacy(), parent.getPrivacy());
        assertEquals(category.getVisibility(), parent.getVisibility());
        assertEquals(category.getName(), parent.getName());
        assertEquals(category.getSubCategories().size(), testCategories.size());
    }


    @Test
    void getById() {
        Category category = categoryService.getById(parent.getId());
        assertEquals(category.getId(), parent.getId());
        assertEquals(category.getDtCreate(), parent.getDtCreate());
        assertEquals(category.getDtUpdate(), parent.getDtUpdate());
        assertEquals(category.getDtDelete(), parent.getDtDelete());
        assertEquals(category.getMeta(), parent.getMeta());
        assertEquals(category.getParentCategory(), parent.getParentCategory());
        assertEquals(category.getDescription(), parent.getDescription());
        assertEquals(category.getIcon(), parent.getIcon());
        assertEquals(category.getDtFrom().getTime(), parent.getDtFrom().getTime());
        assertEquals(category.getDtTo().getTime(), parent.getDtTo().getTime());
        assertEquals(category.getPrivacy(), parent.getPrivacy());
        assertEquals(category.getVisibility(), parent.getVisibility());
        assertEquals(category.getName(), parent.getName());
        assertEquals(category.getSubCategories().size(), testCategories.size());
    }

    @Test
    void findById() {
        Optional<Category> optional = categoryService.findById(parent.getId());

        assertTrue(optional.isPresent());

        Category category = optional.get();
        assertEquals(category.getId(), parent.getId());
        assertEquals(category.getDtCreate(), parent.getDtCreate());
        assertEquals(category.getDtUpdate(), parent.getDtUpdate());
        assertEquals(category.getDtDelete(), parent.getDtDelete());
        assertEquals(category.getMeta(), parent.getMeta());
        assertEquals(category.getParentCategory(), parent.getParentCategory());
        assertEquals(category.getDescription(), parent.getDescription());
        assertEquals(category.getIcon(), parent.getIcon());
        assertEquals(category.getDtFrom().getTime(), parent.getDtFrom().getTime());
        assertEquals(category.getDtTo().getTime(), parent.getDtTo().getTime());
        assertEquals(category.getPrivacy(), parent.getPrivacy());
        assertEquals(category.getVisibility(), parent.getVisibility());
        assertEquals(category.getName(), parent.getName());
        assertEquals(category.getSubCategories().size(), testCategories.size());
    }


    @Test
    void update() {

    }



}
