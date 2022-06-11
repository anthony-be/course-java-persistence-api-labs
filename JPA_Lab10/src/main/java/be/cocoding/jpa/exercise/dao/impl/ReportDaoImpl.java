package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.ReportDao;
import be.cocoding.jpa.exercise.dto.CustomerReportDto;
import be.cocoding.jpa.exercise.entity.customer.Customer;
import be.cocoding.jpa.exercise.entity.order.Order;
import be.cocoding.jpa.exercise.entity.order.OrderItem;
import be.cocoding.jpa.exercise.entity.product.Product;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public class ReportDaoImpl implements ReportDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public BigDecimal getTotalSellLastMonth() {
        TypedQuery<BigDecimal> query = em.createNamedQuery("Order.totalSellLastMonth", BigDecimal.class);
        Date last30Days = DateUtils.addDays(new Date(), -30);
        query.setParameter("createdOn", last30Days, TemporalType.TIMESTAMP);
        return query.getSingleResult();
    }

    @Override
    public BigDecimal getTotalSellLastMonthWithApi() {
        // select sum(o.amount) from Order o where o.createdOn > :createdOn
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
        Root<Order> order = cq.from(Order.class);

        // select sum(o.amount)
        cq.select( cb.sum(order.get("amount")) );

        // where o.CreatedOn > :createdOn
        ParameterExpression<Date> paramCreatedOn = cb.parameter(Date.class, "createdOn");
        Predicate pCreatedOn = cb.greaterThan(order.get("createdOn"), paramCreatedOn);
        cq.where(pCreatedOn);

        // Query
        TypedQuery<BigDecimal> query = em.createQuery(cq);
        Date last30Days = DateUtils.addDays(new Date(), -30);
        query.setParameter("createdOn", last30Days, TemporalType.TIMESTAMP);
        return query.getSingleResult();
    }

    @Override
    public List<Object[]> getTop3CustomerGreatestAmount() {
        TypedQuery<Object[]> query = em.createNamedQuery("Customer.customerTotalAmountOrderByAmountDesc", Object[].class);
        query.setMaxResults(3);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getTop3CustomerGreatestAmountWithApi() {
        // "select c.lastname, c.firstname, sum(o.amount) AS TOTAL_AMOUNT " +
        //                        "from Customer c join c.orders o " +
        //                        "group by c.lastname, c.firstname " +
        //                        "order by TOTAL_AMOUNT DESC"

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Customer> cust = cq.from(Customer.class);
        Join<Customer, Order> order = cust.join("orders");

        Path<Object> custLastname = cust.get("lastname");
        Path<Object> custFirstname = cust.get("firstname");
        Expression<Number> sumOrderAmount = cb.sum(order.get("amount"));
        cq.multiselect(custLastname, custFirstname, sumOrderAmount);
        cq.groupBy(custLastname, custFirstname);
        cq.orderBy(cb.desc(sumOrderAmount));

        TypedQuery<Object[]> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getTop3CustomerGreatestProductNumberLastMonth() {
        TypedQuery<Object[]> query = em.createNamedQuery("Customer.customerTotalProductNumbersOrderByNumberDesc", Object[].class);
        Date date30daysAgo = DateUtils.addDays(new Date(), -30);
        query.setParameter("createdOn", date30daysAgo, TemporalType.TIMESTAMP);
        query.setMaxResults(3);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getTop3CustomerGreatestProductNumberLastMonthWithApi() {
        // select c.lastname, c.firstname, count(p) AS NBR_OF_PRODUCT " +
        //                        "from Customer c join c.orders o join o.items oi join oi.product p " +
        //                        "where o.createdOn >= :createdOn " +
        //                        "group by c.lastname, c.firstname " +
        //                        "order by NBR_OF_PRODUCT DESC
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Customer> cust = cq.from(Customer.class);
        Join<Customer, Order> order = cust.join("orders");
        Join<OrderItem, Product> product = order.join("items").join("product");

        // select c.lastname, c.firstname, count(p)
        Path<Object> custLastname = cust.get("lastname");
        Path<Object> custFirstname = cust.get("firstname");
        Expression<Long> countProduct = cb.count(product);
        cq.multiselect(custLastname, custFirstname, countProduct);

        // where o.createdOn >= :createdOn
        ParameterExpression<Date> paramCreatedOn = cb.parameter(Date.class, "createdOn");
        Predicate pCreatedOn = cb.greaterThanOrEqualTo(order.get("createdOn"), paramCreatedOn);
        cq.where(pCreatedOn);

        // group by c.lastname, c.firstname
        cq.groupBy(custLastname, custFirstname);

        // order by count(p)
        cq.orderBy(cb.desc(countProduct));

        TypedQuery<Object[]> query = em.createQuery(cq);
        Date date30daysAgo = DateUtils.addDays(new Date(), -30);
        query.setParameter("createdOn", date30daysAgo, TemporalType.TIMESTAMP);
        return query.getResultList();
    }

    @Override
    public CustomerReportDto getCustomerStatsLastWeek() {
        // NOTE: Le calcul de l'âge est approximatif dans cette requête et ne sert que d'exemple !

        String jpql = "select   " +
                " new be.cocoding.jpa.exercise.dto.CustomerReportDto(" +
                " count( c )," +
                " avg(length(c.password))," +
                " min(length(c.password))," +
                " max(length(c.password))," +
                " AVG( YEAR(current_date) - YEAR(c.birthDate) )," +
                " MIN( YEAR(current_date) - YEAR(c.birthDate) )," +
                " MAX( YEAR(current_date) - YEAR(c.birthDate) )" +
                ")" +
                "from Customer c where c.createdOn > :date";
        TypedQuery<CustomerReportDto> query = em.createQuery(jpql, CustomerReportDto.class);
        Date date7DaysAgo = DateUtils.addDays(new Date(), -7);
        query.setParameter("date", date7DaysAgo);
        return query.getSingleResult();
    }

    @Override
    public CustomerReportDto getCustomerStatsLastWeekWithApi() {
        // String jpql = "select   " +
        //                " new be.cocoding.jpa.exercise.dto.CustomerReportDto(" +
        //                " count( c )," +
        //                " avg(length(c.password))," +
        //                " min(length(c.password))," +
        //                " max(length(c.password))," +
        //                " AVG( YEAR(current_date) - YEAR(c.birthDate) )," +
        //                " MIN( YEAR(current_date) - YEAR(c.birthDate) )," +
        //                " MAX( YEAR(current_date) - YEAR(c.birthDate) )" +
        //                ")" +
        //                "from Customer c where c.createdOn > :date";

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CustomerReportDto> cq = cb.createQuery(CustomerReportDto.class);
        Root<Customer> cust = cq.from(Customer.class);

        // where c.createdOn > :date
        ParameterExpression<Date> paramDate = cb.parameter(Date.class, "date");
        Predicate pCreatedOn = cb.greaterThan(cust.get("createdOn"), paramDate);
        cq.where(pCreatedOn);

        Path<String> custPassword = cust.get("password");
        Expression<Long> custAgeExpr = cb.diff(cb.function("YEAR", Long.class, cb.currentDate()), cb.function("YEAR", Long.class, cust.get("birthDate")));

        // select
        Expression<Long> countCustomer = cb.count(cust);
        Expression<Double> avgPasswordLength = cb.avg(cb.length(custPassword));
        Expression<Integer> minPasswordLength = cb.min(cb.length(custPassword));
        Expression<Integer> maxPasswordLength = cb.max(cb.length(custPassword));
        Expression<Double> avgCustomerAge = cb.avg(custAgeExpr);
        Expression<Long> minCustomerAge = cb.min(custAgeExpr);
        Expression<Long> maxCustomerAge = cb.max(custAgeExpr);

        cq.select( cb.construct(CustomerReportDto.class,
                countCustomer,
                avgPasswordLength, minPasswordLength, maxPasswordLength,
                avgCustomerAge, minCustomerAge, maxCustomerAge) );

        // Query
        TypedQuery<CustomerReportDto> query = em.createQuery(cq);
        Date date7DaysAgo = DateUtils.addDays(new Date(), -7);
        query.setParameter("date", date7DaysAgo);
        return query.getSingleResult();
    }
}
