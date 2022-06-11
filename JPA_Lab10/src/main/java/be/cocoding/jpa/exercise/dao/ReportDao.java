package be.cocoding.jpa.exercise.dao;

import be.cocoding.jpa.exercise.dto.CustomerReportDto;

import java.math.BigDecimal;
import java.util.List;

public interface ReportDao {

    BigDecimal getTotalSellLastMonth();

    BigDecimal getTotalSellLastMonthWithApi();

    List<Object[]> getTop3CustomerGreatestAmount();

    List<Object[]> getTop3CustomerGreatestAmountWithApi();

    List<Object[]> getTop3CustomerGreatestProductNumberLastMonth();

    List<Object[]> getTop3CustomerGreatestProductNumberLastMonthWithApi();

    CustomerReportDto getCustomerStatsLastWeek();

    CustomerReportDto getCustomerStatsLastWeekWithApi();
}
