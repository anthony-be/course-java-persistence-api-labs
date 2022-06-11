package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.ReportDao;
import be.cocoding.jpa.exercise.dto.CustomerReportDto;
import be.cocoding.jpa.exercise.test.SpringDaoContextTest;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class ReportDaoIT extends SpringDaoContextTest {

    @Autowired
    private ReportDao dao;

    @Test
    public void getTotalSellLastMonth() {
        BigDecimal actual = dao.getTotalSellLastMonth();
        assertNotNull(actual);
        assertEquals(new BigDecimal("1113002"), actual);
    }

    @Test
    public void getTop3CustomerGreatestAmount() {
        List<Object[]> actuals = dao.getTop3CustomerGreatestAmount();
        assertNotNull(actuals);
        assertEquals(3, actuals.size());

        Iterator<Object[]> it = actuals.iterator();
        Object[] actual = it.next();// Top 1
        assertEquals( "Mister", actual[0] );
        assertEquals( "Casino", actual[1] );
        assertEquals( new BigDecimal("1109283"), actual[2] );

        actual = it.next();// Top 2
        assertEquals( "Poirot", actual[0] );
        assertEquals( "Hercule", actual[1] );
        assertEquals( new BigDecimal("3000"), actual[2] );

        actual = it.next();// Top 3
        assertEquals( "Feron", actual[0] );
        assertEquals( "Anthony", actual[1] );
        assertEquals( new BigDecimal("719"), actual[2] );
    }

    @Test
    public void getTop3CustomerGreatestProductNumberLastMonth() {
        List<Object[]> actuals = dao.getTop3CustomerGreatestProductNumberLastMonth();
        assertNotNull(actuals);
        assertEquals(3, actuals.size());

        Iterator<Object[]> it = actuals.iterator();
        Object[] actual = it.next();// Top 1
        assertEquals( "Mister", actual[0] );
        assertEquals( "Casino", actual[1] );
        assertEquals( Long.valueOf(12309), actual[2] );

        actual = it.next();// Top 2
        assertEquals( "Poirot", actual[0] );
        assertEquals( "Hercule", actual[1] );
        assertEquals( Long.valueOf(3), actual[2] );

        actual = it.next();// Top 3
        assertEquals( "Feron", actual[0] );
        assertEquals( "Anthony", actual[1] );
        assertEquals( Long.valueOf(2), actual[2] );
    }

    @Test
    public void getCustomerStatsLastWeek() {
        CustomerReportDto actual = dao.getCustomerStatsLastWeek();
        assertNotNull(actual);
        assertEquals(Long.valueOf(5), actual.getNbrOfCustomer());
        assertEquals(Double.valueOf(7.6), actual.getPasswordAverageLength());
        assertEquals(Integer.valueOf(3), actual.getPasswordMinimumLength());
        assertEquals(Integer.valueOf(15), actual.getPasswordMaximumLength());
        assertEquals(Double.valueOf(58.2), actual.getCustomerAverageAge());
        assertEquals(Integer.valueOf(22), actual.getCustomerYoungestAge());
        assertEquals(Integer.valueOf(102), actual.getCustomerOldestAge());
    }

    @Test
    public void getTotalSellLastMonthWithApi() {
        BigDecimal actual = dao.getTotalSellLastMonthWithApi();
        assertNotNull(actual);
        assertEquals(new BigDecimal("1113002"), actual);
    }

    @Test
    public void getTop3CustomerGreatestAmountWithApi() {
        List<Object[]> actuals = dao.getTop3CustomerGreatestAmountWithApi();
        assertNotNull(actuals);
        assertEquals(3, actuals.size());

        Iterator<Object[]> it = actuals.iterator();
        Object[] actual = it.next();// Top 1
        assertEquals( "Mister", actual[0] );
        assertEquals( "Casino", actual[1] );
        assertEquals( new BigDecimal("1109283"), actual[2] );

        actual = it.next();// Top 2
        assertEquals( "Poirot", actual[0] );
        assertEquals( "Hercule", actual[1] );
        assertEquals( new BigDecimal("3000"), actual[2] );

        actual = it.next();// Top 3
        assertEquals( "Feron", actual[0] );
        assertEquals( "Anthony", actual[1] );
        assertEquals( new BigDecimal("719"), actual[2] );
    }

    @Test
    public void getTop3CustomerGreatestProductNumberLastMonthWithApi() {
        List<Object[]> actuals = dao.getTop3CustomerGreatestProductNumberLastMonthWithApi();
        assertNotNull(actuals);
        assertEquals(3, actuals.size());

        Iterator<Object[]> it = actuals.iterator();
        Object[] actual = it.next();// Top 1
        assertEquals( "Mister", actual[0] );
        assertEquals( "Casino", actual[1] );
        assertEquals( Long.valueOf(12309), actual[2] );

        actual = it.next();// Top 2
        assertEquals( "Poirot", actual[0] );
        assertEquals( "Hercule", actual[1] );
        assertEquals( Long.valueOf(3), actual[2] );

        actual = it.next();// Top 3
        assertEquals( "Feron", actual[0] );
        assertEquals( "Anthony", actual[1] );
        assertEquals( Long.valueOf(2), actual[2] );

        actuals.stream().forEach(e -> {
            System.out.println(ArrayUtils.toString(e));
        });
    }

    @Test
    public void getCustomerStatsLastWeekWithApi() {
        CustomerReportDto actual = dao.getCustomerStatsLastWeekWithApi();
        assertNotNull(actual);
        assertEquals(Long.valueOf(5), actual.getNbrOfCustomer());
        assertEquals(Double.valueOf(7.6), actual.getPasswordAverageLength());
        assertEquals(Integer.valueOf(3), actual.getPasswordMinimumLength());
        assertEquals(Integer.valueOf(15), actual.getPasswordMaximumLength());
        assertEquals(Double.valueOf(58.2), actual.getCustomerAverageAge());
        assertEquals(Integer.valueOf(22), actual.getCustomerYoungestAge());
        assertEquals(Integer.valueOf(102), actual.getCustomerOldestAge());
    }
}