package be.cocoding.jpa.exercise.dao;

import be.cocoding.jpa.exercise.entity.product.Category;
import be.cocoding.jpa.exercise.entity.product.Product;
import be.cocoding.jpa.exercise.entity.product.ProductStatus;

import java.math.BigDecimal;

public interface ProductDao {

    Product findById(int productId);

    Category addProductCategory(String name, String description);

    void deleteProductCategory( Category pc);

    Product addProduct(String name, String description, BigDecimal price, String sku, int categoryId, int initialNumberOfItems);

    void changeProductStatus(int productId, ProductStatus status);

    void increaseInventory(Product product, int numberOfItems);

    void decreaseInventory(Product product, int numberOfItems);
}
