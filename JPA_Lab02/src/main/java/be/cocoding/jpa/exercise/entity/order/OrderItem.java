package be.cocoding.jpa.exercise.entity.order;

import be.cocoding.jpa.exercise.entity.product.Product;

public class OrderItem {

    private Integer id;

    private Order order;

    private Product produt;

    private Integer quantity;

    public OrderItem() {
    }

    public Integer getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProdut() {
        return produt;
    }

    public void setProdut(Product produt) {
        this.produt = produt;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
