package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.OrderDao;
import be.cocoding.jpa.exercise.entity.order.Order;
import be.cocoding.jpa.exercise.entity.order.OrderStatus;
import be.cocoding.jpa.exercise.test.SpringDaoContextTest;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

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

    @Test
    public void findByIdNamedQuery() {
        Order actual = orderDao.findByIdNamedQuery(1);
        assertNotNull(actual);
        assertEquals(new BigDecimal("179"), actual.getAmount());
        assertEquals(OrderStatus.DELIVERED, actual.getStatus());
    }

    @Test
    public void findByCriteria() {
        String customerUsername = "Hercule";
        String paymentProvider = "MASTERCARD";
        Collection<OrderStatus> statuses = Arrays.asList(OrderStatus.DELIVERED, OrderStatus.ORDERED);
        BigDecimal amountFrom = new BigDecimal("1000");
        BigDecimal amountTo = new BigDecimal("4000");
        Collection<Integer> productIds = Arrays.asList(1, 9, 15);
        Date createdFrom = DateUtils.addDays(new Date(), -1); // Yesterday
        Date createdTo = null;

        Collection<Order> actuals = orderDao.findByCriteria(customerUsername, paymentProvider, statuses, amountFrom, amountTo, productIds, createdFrom, createdTo);

        assertNotNull(actuals);
        assertEquals(1, actuals.size());
        Order actual = actuals.iterator().next();
        assertEquals(Integer.valueOf(3), actual.getId());
    }

    @Test
    @Transactional
    public void deleteOrders() {
        orderDao.deleteOrders(Arrays.asList(1, 2, 3));
    }
}