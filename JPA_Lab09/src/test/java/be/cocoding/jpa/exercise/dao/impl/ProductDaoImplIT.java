package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.ProductDao;
import be.cocoding.jpa.exercise.entity.product.Category;
import be.cocoding.jpa.exercise.entity.product.Product;
import be.cocoding.jpa.exercise.entity.product.ProductStatus;
import be.cocoding.jpa.exercise.test.SpringDaoContextTest;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

public class ProductDaoImplIT extends SpringDaoContextTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ProductDao dao;

    @Test
    public void findById() {
        Product actual = dao.findById(1);
        assertNotNull(actual);
        assertEquals("Samsung LS24R650", actual.getName());
        assertEquals("Samsung LS24R650 description", actual.getDescription());
        assertEquals("LS24R650", actual.getSku());
        assertEquals(new BigDecimal("179"), actual.getPrice());
        assertEquals(ProductStatus.PUBLISHED, actual.getStatus());
    }

    @Test
    public void findByIdNamedQuery() {
        Product actual = dao.findByIdNamedQuery(1);
        assertNotNull(actual);
        assertEquals("Samsung LS24R650", actual.getName());
        assertEquals("Samsung LS24R650 description", actual.getDescription());
        assertEquals("LS24R650", actual.getSku());
        assertEquals(new BigDecimal("179"), actual.getPrice());
        assertEquals(ProductStatus.PUBLISHED, actual.getStatus());
    }

    @Test
    @Transactional
    public void addProductCategory() {
        Category category = dao.addProductCategory("Dummy Cat", "Dummy category description");
        assertNotNull(category);

        Category actual = em.find(Category.class, category.getId());
        assertNotNull(actual);
        assertEquals("Dummy Cat", actual.getName());
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void deleteProductCategoryLinkedToProduct() {
        Category category = em.find(Category.class, 1);// Category 1 est lié à des Product
        dao.deleteProductCategory(category);
        em.flush(); // Force la synchronisation et l'erreur de contrainte
    }

    @Test
    @Transactional
    public void deleteProductCategory() {
        Category category = em.find(Category.class, 5);// Category 5 n'est lié à aucun Product
        dao.deleteProductCategory(category);
        em.flush(); // Force la synchronisation DB
    }

    @Test
    @Transactional
    public void addProduct() {
        Product product = dao.addProduct("Dummy product", "Dummy product description", new BigDecimal("155"),
                "Dummy sku", 1, 50);
        assertNotNull(product);

        Product actual = em.find(Product.class, product.getId());
        assertNotNull(actual);
        assertEquals("Dummy product", actual.getName());
    }

    @Test
    @Transactional
    public void changeProductStatus() {
        int productId = 1;
        dao.changeProductStatus(productId, ProductStatus.OUT_OF_CATALOG);

        em.flush();
        em.clear();

        Product product = dao.findById(productId);
        assertNotNull(product);
        assertEquals(ProductStatus.OUT_OF_CATALOG, product.getStatus());
    }

    @Test
    @Transactional
    public void increaseInventory() {
        Product product = em.find(Product.class, 1);
        int originalQty = product.getInventory().getQuantity();
        dao.increaseInventory( product, 27 );

        em.flush();
        em.clear();

        Product actual = em.find(Product.class, 1);
        assertEquals( Integer.valueOf(originalQty+27),  actual.getInventory().getQuantity());
    }

    @Test
    @Transactional
    public void decreaseInventory() {
        Product product = em.find(Product.class, 1);
        int originalQty = product.getInventory().getQuantity();
        dao.decreaseInventory( product, 2 );

        em.flush();
        em.clear();

        Product actual = em.find(Product.class, 1);
        assertEquals( Integer.valueOf(originalQty-2),  actual.getInventory().getQuantity());
    }

    @Test
    public void findAll() {
        Collection<Product> actuals = dao.findAll();
        assertNotNull(actuals);
        assertFalse(actuals.isEmpty());
    }

    @Test
    public void findByCriteria() {
        String categoryName = "Monitor";
        String productName = "Samsung LS24R650";
        String productDescription = "Samsung";
        BigDecimal priceFrom = new BigDecimal("120");
        BigDecimal priceTo = new BigDecimal("190");
        Collection<ProductStatus> statuses = Arrays.asList(ProductStatus.PUBLISHED, ProductStatus.OUT_OF_CATALOG);
        boolean inStock = true;
        Date createdFrom = DateUtils.addDays(new Date(), -1); // Yesterday
        Date createdTo = DateUtils.addDays(new Date(), 1);// Tomorrow
        Collection<Product> actuals = dao.findByCriteria(categoryName, productName, productDescription, priceFrom, priceTo, statuses, inStock, createdFrom, createdTo);
        assertNotNull(actuals);
        assertEquals(1, actuals.size());
        Product actual = actuals.iterator().next();
        assertEquals(Integer.valueOf(1), actual.getId());
    }

    @Test
    @Transactional
    public void deleteUnusedCategories() {
        int actual = dao.deleteUnusedCategories();
        assertEquals(1, actual);
    }

    @Test
    public void findAllWithApi() {
        Collection<Product> actuals = dao.findAllWithApi();
        assertNotNull(actuals);
        assertFalse(actuals.isEmpty());
    }

    @Test
    public void findByCriteriaWithApi() {
        String categoryName = "Monitor";
        String productName = "Samsung LS24R650";
        String productDescription = "Samsung";
        BigDecimal priceFrom = new BigDecimal("120");
        BigDecimal priceTo = new BigDecimal("190");
        Collection<ProductStatus> statuses = Arrays.asList(ProductStatus.PUBLISHED, ProductStatus.OUT_OF_CATALOG);
        boolean inStock = true;
        Date createdFrom = DateUtils.addDays(new Date(), -1); // Yesterday
        Date createdTo = DateUtils.addDays(new Date(), 1);// Tomorrow

        Collection<Product> actuals = dao.findByCriteriaWithApi(categoryName, productName, productDescription, priceFrom, priceTo, inStock, createdFrom, createdTo);

        assertNotNull(actuals);
        assertEquals(1, actuals.size());
        Product actual = actuals.iterator().next();
        assertEquals(Integer.valueOf(1), actual.getId());
    }
}