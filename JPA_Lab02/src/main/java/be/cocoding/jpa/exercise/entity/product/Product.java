package be.cocoding.jpa.exercise.entity.product;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Product {

    private Integer id;

    private String name;

    private String description;

    private BigDecimal price;

    private String sku;

    private ProductStatus status;

    private Category category;

    private Inventory inventory;

    private Timestamp createdOn;

    private Timestamp updatedOn;

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(price, product.price) && Objects.equals(sku, product.sku) && status == product.status && Objects.equals(category, product.category) && Objects.equals(inventory, product.inventory) && Objects.equals(createdOn, product.createdOn) && Objects.equals(updatedOn, product.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, sku, status, category, inventory, createdOn, updatedOn);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("description", description)
                .append("price", price)
                .append("sku", sku)
                .append("status", status)
//                .append("category", category)
//                .append("inventory", inventory)
                .append("createdOn", createdOn)
                .append("updatedOn", updatedOn)
                .toString();
    }
}
