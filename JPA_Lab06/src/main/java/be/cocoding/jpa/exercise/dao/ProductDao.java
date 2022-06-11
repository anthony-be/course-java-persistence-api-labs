package be.cocoding.jpa.exercise.dao;

import be.cocoding.jpa.exercise.entity.product.Category;
import be.cocoding.jpa.exercise.entity.product.Product;
import be.cocoding.jpa.exercise.entity.product.ProductStatus;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public interface ProductDao {

    Product findById(int productId);

    Product findByIdNamedQuery(int productId);

    Category addProductCategory(String name, String description);

    void deleteProductCategory( Category pc);

    Product addProduct(String name, String description, BigDecimal price, String sku, int categoryId, int initialNumberOfItems);

    void changeProductStatus(int productId, ProductStatus status);

    void increaseInventory(Product product, int numberOfItems);

    void decreaseInventory(Product product, int numberOfItems);

    Collection<Product> findAll();

    Collection<Product> findByCriteria(String categoryName, String name, String description, BigDecimal priceFrom, BigDecimal priceTo, Collection<ProductStatus> statuses, boolean inStock, Date createdFrom, Date createdTo);

    int deleteUnusedCategories();
}
