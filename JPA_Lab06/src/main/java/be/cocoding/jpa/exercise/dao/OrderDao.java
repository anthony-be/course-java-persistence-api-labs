package be.cocoding.jpa.exercise.dao;

import be.cocoding.jpa.exercise.entity.order.Order;
import be.cocoding.jpa.exercise.entity.order.OrderStatus;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public interface OrderDao {
    Order findById(int orderId);

    Order findByIdNamedQuery(int orderId);

    Collection<Order> findByCriteria(String customerUsername, String paymentProvider, Collection<OrderStatus> statuses, BigDecimal amountFrom, BigDecimal amountTo, Collection<Integer> productIds, Date createdFrom, Date createdTo);

    void deleteOrders(Collection<Integer> orderIds);
}
