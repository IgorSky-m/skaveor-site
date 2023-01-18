package com.skachko.shop.auth.service.entities.caterogy.service;

import com.skachko.shop.auth.service.entities.caterogy.dto.Category;
import com.skachko.shop.auth.service.entities.caterogy.repository.ICategoryRepository;
import com.skachko.shop.auth.service.entities.caterogy.service.api.ACategoryServiceTest;
import com.skachko.shop.auth.service.entities.caterogy.service.api.ICategoryService;
import com.skachko.shop.auth.service.libraries.mvc.api.AEntity;
import com.skachko.shop.auth.service.libraries.search.SearchCriteria;
import com.skachko.shop.auth.service.libraries.search.SearchPredicate;
import com.skachko.shop.auth.service.libraries.search.api.EComparisonOperator;
import com.skachko.shop.auth.service.libraries.search.api.EPredicateOperator;
import com.skachko.shop.auth.service.libraries.search.api.ISearchExpression;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableTransactionManagement
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class CategoryServiceTest extends ACategoryServiceTest {

    private List<Category> testCategories;

    private Date dtCreateTest;
    private Category parent;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ICategoryRepository repository;

    @BeforeAll
    public void setupAll() {
        dtCreateTest = new Date(System.currentTimeMillis() - 100000000);
        parent = categoryService.save(getTestCategory(), dtCreateTest);

        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            categories.add(getTestCategory(parent, Integer.toString(i)));
        }
        List<Category> categories1 = categoryService.save(categories, dtCreateTest);
        testCategories = new ArrayList<>(categories1);



    }


    @BeforeEach
    public void setup() {
        categoryService.save(parent, dtCreateTest);
        categoryService.save(new ArrayList<>(testCategories), dtCreateTest);
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
        criteria.setSearchPredicate(predicate);

        Optional<Category> optional = categoryService.findOne(criteria);
        assertTrue(optional.isPresent());

        Category category = optional.get();
        assertEquals(category.getId(), parent.getId());
        assertEquals(category.getDtCreate().getTime(), dtCreateTest.getTime());
        assertEquals(category.getDtUpdate().getTime(), parent.getDtUpdate().getTime());
        assertNull(category.getDtDelete());
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

        for (Category element : testCategories) {

            SearchCriteria cr = new SearchCriteria();
            SearchPredicate pr = new SearchPredicate();
            pr.setConditionOperator(EPredicateOperator.AND);
            List<ISearchExpression> expr = new ArrayList<>();
            expr.add(ISearchExpression.of(AEntity.ID, EComparisonOperator.EQUAL, element.getId()));
            pr.setSearchExpressions(expr);
            cr.setSearchPredicate(pr);


            Optional<Category> testCategoryOptional = categoryService.findOne(cr);

            assertTrue(testCategoryOptional.isPresent());

            Category testCategory = testCategoryOptional.get();

            assertEquals(testCategory.getId(), element.getId());
            assertEquals(testCategory.getDtCreate().getTime(), element.getDtCreate().getTime());
            assertEquals(testCategory.getDtUpdate().getTime(), element.getDtUpdate().getTime());
            assertNull(testCategory.getDtDelete());
            assertEquals(testCategory.getMeta(), element.getMeta());
            assertEquals(testCategory.getParentCategory().getId(), element.getParentCategory().getId());
            assertEquals(testCategory.getDescription(), element.getDescription());
            assertEquals(testCategory.getIcon(), element.getIcon());
            assertEquals(testCategory.getDtFrom().getTime(), element.getDtFrom().getTime());
            assertEquals(testCategory.getDtTo().getTime(), element.getDtTo().getTime());
            assertEquals(testCategory.getPrivacy(), element.getPrivacy());
            assertEquals(testCategory.getVisibility(), element.getVisibility());
            assertEquals(testCategory.getName(), element.getName());
            assertNotNull(testCategory.getSubCategories());
            assertTrue(testCategory.getSubCategories().isEmpty());
        }

    }


    @Test
    void getById() {
        Category category = categoryService.getById(parent.getId());
        assertEquals(category.getId(), parent.getId());
        assertEquals(category.getDtCreate().getTime(), parent.getDtCreate().getTime());
        assertEquals(category.getDtUpdate().getTime(), parent.getDtUpdate().getTime());
        assertNull(category.getDtDelete());
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


        for (Category element : testCategories) {
            Category testCategory = categoryService.getById(element.getId());
            assertEquals(testCategory.getId(), element.getId());
            assertEquals(category.getDtCreate().getTime(), element.getDtCreate().getTime());
            assertEquals(category.getDtUpdate().getTime(), element.getDtUpdate().getTime());
            assertNull(category.getDtDelete());
            assertEquals(testCategory.getMeta(), element.getMeta());
            assertEquals(testCategory.getParentCategory().getId(), element.getParentCategory().getId());
            assertEquals(testCategory.getDescription(), element.getDescription());
            assertEquals(testCategory.getIcon(), element.getIcon());
            assertEquals(testCategory.getDtFrom().getTime(), element.getDtFrom().getTime());
            assertEquals(testCategory.getDtTo().getTime(), element.getDtTo().getTime());
            assertEquals(testCategory.getPrivacy(), element.getPrivacy());
            assertEquals(testCategory.getVisibility(), element.getVisibility());
            assertEquals(testCategory.getName(), element.getName());
            assertNotNull(testCategory.getSubCategories());
            assertTrue(testCategory.getSubCategories().isEmpty());
        }
    }

    @Test
    void findById() {
        Optional<Category> optional = categoryService.findById(parent.getId());

        assertTrue(optional.isPresent());

        Category category = optional.get();
        assertEquals(category.getId(), parent.getId());
        assertEquals(category.getDtCreate().getTime(), parent.getDtCreate().getTime());
        assertEquals(category.getDtUpdate().getTime(), parent.getDtUpdate().getTime());
        assertNull(category.getDtDelete());
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

        for (Category element : testCategories) {
            Optional<Category> testCategoryOptional = categoryService.findById(element.getId());

            assertTrue(testCategoryOptional.isPresent());

            Category testCategory = testCategoryOptional.get();

            assertEquals(testCategory.getId(), element.getId());
            assertEquals(category.getDtCreate().getTime(), element.getDtCreate().getTime());
            assertEquals(category.getDtUpdate().getTime(), element.getDtUpdate().getTime());
            assertNull(category.getDtDelete());
            assertEquals(testCategory.getMeta(), element.getMeta());
            assertEquals(testCategory.getParentCategory().getId(), element.getParentCategory().getId());
            assertEquals(testCategory.getDescription(), element.getDescription());
            assertEquals(testCategory.getIcon(), element.getIcon());
            assertEquals(testCategory.getDtFrom().getTime(), element.getDtFrom().getTime());
            assertEquals(testCategory.getDtTo().getTime(), element.getDtTo().getTime());
            assertEquals(testCategory.getPrivacy(), element.getPrivacy());
            assertEquals(testCategory.getVisibility(), element.getVisibility());
            assertEquals(testCategory.getName(), element.getName());
            assertNotNull(testCategory.getSubCategories());
            assertTrue(testCategory.getSubCategories().isEmpty());
        }
    }


    @Test
    void update() {

        //Test update parent category
        UUID categoryIdForRemoveParent = testCategories.stream()
                .findFirst()
                .map(AEntity::getId)
                .orElseThrow(IllegalArgumentException::new);

        Category removedParent = categoryService.getById(categoryIdForRemoveParent);
        assertNotNull(removedParent.getParentCategory());

        assertNotNull(removedParent.getSubCategories());
        assertTrue(removedParent.getSubCategories().isEmpty());

        removedParent.setParentCategory(null);
        String expectedName = removedParent.getName();
        Date expectedDate = new Date(removedParent.getDtUpdate().getTime());
        Category update = categoryService.update(removedParent.getId(), expectedDate, removedParent);
        assertEquals(update.getName(), expectedName);
        assertNotEquals(update.getDtUpdate().getTime(), expectedDate.getTime());
        assertNull(update.getParentCategory());


        //Test update sub categories
        String prefix = "updated_";
        Category categoryForUpdate = getTestCategory(prefix);

        categoryForUpdate.setParentCategory(update);

        UUID parentId= parent.getId();
        Date parentDtUpdate = parent.getDtUpdate();


        Category update1 = categoryService.update(parentId, parentDtUpdate, categoryForUpdate);
        assertNotEquals(parentDtUpdate, update1.getDtUpdate());
        assertNotEquals(parent.getName(), update1.getName());
        assertTrue(update1.getName().startsWith(prefix));
        assertNotNull(update1.getParentCategory());
        assertEquals(update1.getParentCategory().getId(), update.getId());


        Category testSubCategories = categoryService.getById(update.getId());
        assertNotNull(testSubCategories.getSubCategories());
        assertEquals(testSubCategories.getSubCategories().size(), 1);

        Category subCategory = testSubCategories.getSubCategories()
                .stream()
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        assertEquals(subCategory.getId(), parentId);
    }


}
