package com.skachko.shop.order.service.entities.caterogy.service;

import com.skachko.shop.order.service.entities.caterogy.dto.Category;
import com.skachko.shop.order.service.entities.caterogy.repository.ICategoryRepository;
import com.skachko.shop.order.service.entities.caterogy.service.api.ACategoryServiceTest;
import com.skachko.shop.order.service.entities.caterogy.service.api.ICategoryService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableTransactionManagement
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class CategoryServiceTestCreate extends ACategoryServiceTest {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ICategoryRepository repository;


    @AfterAll
    public void shutDown() {
        repository.deleteAll();
    }

    @AfterEach
    public void reset() {
        repository.deleteAll();
    }


    @Test
    @DisplayName("save one without date")
    void saveWithoutDate() {
        Category withoutDate = getTestCategory();
        Date expectedDtCreate = withoutDate.getDtCreate();
        Date expectedDtUpdate = withoutDate.getDtUpdate();

        Category result = categoryService.save(withoutDate);

        assertNotNull(result.getDtCreate());
        assertNotNull(result.getDtUpdate());
        assertNotEquals(expectedDtCreate, result.getDtCreate());
        assertNotEquals(expectedDtUpdate, result.getDtUpdate());
        assertNotNull(result.getId());
        assertEquals(result.getDtCreate(), result.getDtUpdate());
        assertNull(result.getDtDelete());
        assertEquals(withoutDate.getName(), result.getName());
        assertEquals(NAME_FIELD_VALUE, result.getName());
        assertEquals(META_FIELD_VALUE, result.getMeta());
        assertEquals(DESCRIPTION_FIELD_VALUE, result.getDescription());


    }


    @Test
    @DisplayName("save one with date")
    void saveWithDate() {
        Date withDateTestDate = new Date();
        Category withDate = getTestCategory(withDateTestDate);
        Date expectedDtCreate = withDate.getDtCreate();
        Date expectedDtUpdate = withDate.getDtUpdate();

        Category result = categoryService.save(withDate, withDateTestDate);

        assertNotNull(result.getDtCreate());
        assertNotNull(result.getDtUpdate());
        assertEquals(expectedDtCreate, result.getDtCreate());
        assertEquals(expectedDtUpdate, result.getDtUpdate());
        assertNotNull(result.getId());
        assertEquals(result.getDtCreate(), result.getDtUpdate());
        assertNull(result.getDtDelete());
        assertEquals(withDate.getName(), result.getName());
        assertEquals(NAME_FIELD_VALUE, result.getName());
        assertEquals(META_FIELD_VALUE, result.getMeta());
        assertEquals(DESCRIPTION_FIELD_VALUE, result.getDescription());


    }

    @Test
    @DisplayName("save all without date")
    void saveAllWithoutDate() {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            categories.add(getTestCategory());
        }

        List<Category> save = categoryService.save(categories);

        assertEquals(save.size(), categories.size());
        assertEquals(save.size(), 10);
        save.forEach(e -> {
            assertNotNull(e.getDtCreate());
            assertNotNull(e.getDtUpdate());
            assertNotNull(e.getId());
            assertEquals(e.getDtCreate(), e.getDtUpdate());
            assertNull(e.getDtDelete());
            assertEquals(NAME_FIELD_VALUE, e.getName());
            assertEquals(DESCRIPTION_FIELD_VALUE, e.getDescription());
            assertEquals(META_FIELD_VALUE, e.getMeta());
        });

    }

    @Test
    @DisplayName("save all with dates")
    public void saveAllWithDates() {
        Date date = new Date();
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            categories.add(getTestCategory(date));
        }

        List<Category> save = categoryService.save(categories);

        assertEquals(save.size(), categories.size());
        assertEquals(save.size(), 10);
        save.forEach(e -> {
            assertNotNull(e.getDtCreate());
            assertNotNull(e.getDtUpdate());
            assertEquals(date, e.getDtCreate());
            assertNotNull(e.getId());
            assertEquals(e.getDtCreate(), e.getDtUpdate());
            assertNull(e.getDtDelete());
            assertEquals(NAME_FIELD_VALUE, e.getName());
            assertEquals(DESCRIPTION_FIELD_VALUE, e.getDescription());
            assertEquals(META_FIELD_VALUE, e.getMeta());
        });
    }

}