package be.cocoding.jpa.exercise.dao;

import be.cocoding.jpa.exercise.entity.order.Order;

public interface OrderDao {

    Order findById(int orderId);
}
