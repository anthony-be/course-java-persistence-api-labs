package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.OrderDao;
import be.cocoding.jpa.exercise.entity.order.Order;
import be.cocoding.jpa.exercise.entity.order.OrderStatus;
import be.cocoding.jpa.exercise.test.SpringDaoContextTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OrderDaoImplIT extends SpringDaoContextTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    @Transactional
    public void findById() {
        Order actual = orderDao.findById(1);
        assertNotNull(actual);
        assertEquals(new BigDecimal("179"), actual.getAmount());
        assertEquals(OrderStatus.DELIVERED, actual.getStatus());
    }
}