package be.cocoding.jpa.exercise.entity.order;

import be.cocoding.jpa.exercise.entity.customer.Customer;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CUST_ID")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "PAYMENT_ID")
    private PaymentDetail paymentDetail;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Basic
    private BigDecimal amount;

    @Column(name = "CREATED_ON")
    private Timestamp createdOn;

    @Column(name = "UPDATED_ON")
    private Timestamp updatedOn;

    @OneToMany(mappedBy = "order")
    private Set<OrderItem> items;

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public PaymentDetail getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(customer, order.customer)
                && Objects.equals(paymentDetail, order.paymentDetail) && status == order.status
                && Objects.equals(amount, order.amount) && Objects.equals(createdOn, order.createdOn)
                && Objects.equals(updatedOn, order.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, paymentDetail, status, amount, createdOn, updatedOn);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
//                .append("customer", customer)
//                .append("paymentDetail", paymentDetail)
                .append("status", status)
                .append("amount", amount)
                .append("createdOn", createdOn)
                .append("updatedOn", updatedOn)
                .toString();
    }
}
