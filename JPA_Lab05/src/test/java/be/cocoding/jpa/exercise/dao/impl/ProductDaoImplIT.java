package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.ProductDao;
import be.cocoding.jpa.exercise.entity.product.Category;
import be.cocoding.jpa.exercise.entity.product.Product;
import be.cocoding.jpa.exercise.entity.product.ProductStatus;
import be.cocoding.jpa.exercise.test.SpringDaoContextTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collection;

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
}