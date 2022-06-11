package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.OrderDao;
import be.cocoding.jpa.exercise.entity.order.Order;
import be.cocoding.jpa.exercise.entity.order.OrderStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Repository("orderDao")
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Order findById(int orderId) {
        return em.find(Order.class, orderId);
    }

    @Override
    public Order findByIdNamedQuery(int orderId) {
        TypedQuery<Order> query = em.createNamedQuery("Order.findById", Order.class);
        query.setParameter("id", orderId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public Collection<Order> findByCriteria(String customerUsername, String paymentProvider, Collection<OrderStatus> statuses, BigDecimal amountFrom, BigDecimal amountTo, Collection<Integer> productIds, Date createdFrom, Date createdTo) {
        TypedQuery<Order> query = em.createNamedQuery("Order.findByCriteria", Order.class);
        query.setParameter(1, customerUsername);
        query.setParameter(2, paymentProvider);
        query.setParameter(3, statuses);
        query.setParameter(4, amountFrom);
        query.setParameter(5, amountTo);
        query.setParameter(6, productIds);
        query.setParameter(7, createdFrom, TemporalType.TIMESTAMP);
        query.setParameter(8, createdTo, TemporalType.TIMESTAMP);
        return query.getResultList();
    }

    @Override
    public void deleteOrders(Collection<Integer> orderIds) {
        // Attention, il faut supprimer les Order Item en premier, avant de supprimer les Order.
        // Autrement, une ConstraintViolation de FK est lancée par la base de données

        // ********** FIX **************
        String jpqlItems = "delete from OrderItem oi where oi.order.id in (:orderIds)";
        Query queryItems = em.createQuery(jpqlItems);
        queryItems.setParameter("orderIds", orderIds);
        queryItems.executeUpdate();
        // ********** FIN FIX **************

        // Suppression des Orders
        String jpql = "delete from Order o where o.id IN (:orderIds)";
        Query query = em.createQuery(jpql);
        query.setParameter("orderIds", orderIds);
        query.executeUpdate();
    }

}
