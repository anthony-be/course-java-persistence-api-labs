package be.cocoding.jpa.exercise.entity.product;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "Product.sequence", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
@NamedQueries({
        @NamedQuery(name = "Product.findById", query = "select p from Product p where p.id = :id"),
        @NamedQuery(name = "Product.findByCriteria", query = "select p from Product p " +
                "where (p.category.name = :categoryName OR :categoryName is null) " +
                "and (upper(p.name) = :productName OR :productName is null) " +
                "and (upper(p.description) like concat('%', :productDescription, '%') OR :productDescription is null) " +
                "and (p.price >= :priceFrom OR :priceFrom is null) " +
                "and (p.price <= :priceTo OR :priceTo is null) " +
                "and (p.status IN (:statuses) OR :statuses is null) " +
                "and (:inStock = true AND p.inventory.quantity > 0 " +
                "   OR :inStock = false AND p.inventory.quantity = 0" +
                "   OR :inStock is null) " +
                "and (p.createdOn >= :createdFrom or :createdFrom is null) " +
                "and (p.createdOn <= :createdTo or :createdTo is null)")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Product.sequence")
    private Integer id;

    @Basic
    private String name;

    @Basic
    private String description;

    @Basic
    private BigDecimal price;

    @Basic
    private String sku;

    @Basic
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToOne
    @JoinColumn(name = "INVENTORY_ID")
    private Inventory inventory;

    @Column(name = "CREATED_ON")
    private Timestamp createdOn;

    @Column(name = "UPDATED_ON")
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
