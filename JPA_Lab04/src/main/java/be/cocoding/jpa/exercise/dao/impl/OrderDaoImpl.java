package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.OrderDao;
import be.cocoding.jpa.exercise.entity.order.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("orderDao")
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Order findById(int orderId) {
        return em.find(Order.class, orderId);
    }

}
