package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.ReportDao;
import be.cocoding.jpa.exercise.dto.CustomerReportDto;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
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
    public List<Object[]> getTop3CustomerGreatestAmount() {
        TypedQuery<Object[]> query = em.createNamedQuery("Customer.customerTotalAmountOrderByAmountDesc", Object[].class);
        query.setMaxResults(3);
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
}
