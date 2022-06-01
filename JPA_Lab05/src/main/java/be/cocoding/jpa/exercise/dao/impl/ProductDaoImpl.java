package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.ProductDao;
import be.cocoding.jpa.exercise.entity.product.Category;
import be.cocoding.jpa.exercise.entity.product.Inventory;
import be.cocoding.jpa.exercise.entity.product.Product;
import be.cocoding.jpa.exercise.entity.product.ProductStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Collection;

@Repository("productDao")
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Product findById(int productId) {
        return em.find(Product.class, productId);
    }

    @Override
    public Category addProductCategory(String name, String description) {
        Category cat = new Category();
        cat.setName(name);
        cat.setDescription(description);
        em.persist(cat);
        return cat;
    }

    @Override
    public void deleteProductCategory(Category cat) {
        Category managed = em.find(Category.class, cat.getId());
        em.remove(managed);
    }

    @Override
    public Product addProduct(String name, String description, BigDecimal price, String sku, int categoryId, int initialNumberOfItems) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setSku(sku);
        Category category = em.find(Category.class, categoryId);
        product.setCategory(category);

        Inventory inventory = new Inventory();
        inventory.setQuantity(initialNumberOfItems);
        em.persist(inventory);

        product.setInventory(inventory);
        em.persist(product);

        return product;
    }

    @Override
    public void changeProductStatus(int productId, ProductStatus status) {
        Product product = em.find(Product.class, productId);
        product.setStatus(status);
        em.merge(product); // Cet appel n'a aucun effet, comme product est déjà Managed
    }

    @Override
    public void increaseInventory(Product product, int numberOfItems) {
        Product managed = em.find(Product.class, product.getId());
        Inventory inventory = managed.getInventory();
        Integer currentQty = inventory.getQuantity();
        inventory.setQuantity(currentQty + numberOfItems);
    }

    @Override
    public void decreaseInventory(Product product, int numberOfItems) {
        Product managed = em.find(Product.class, product.getId());
        Inventory inventory = managed.getInventory();
        Integer currentQty = inventory.getQuantity();
        inventory.setQuantity(currentQty - numberOfItems);
        em.merge(managed); // Cet appel ne fait rien, comme product est déjà Managed
    }

    @Override
    public Collection<Product> findAll() {
        TypedQuery<Product> query = em.createQuery("select p from Product p", Product.class);
        return query.getResultList();
    }
}
