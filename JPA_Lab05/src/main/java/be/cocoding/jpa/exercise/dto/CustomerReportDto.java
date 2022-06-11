package be.cocoding.jpa.exercise.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class CustomerReportDto {

    private final Long nbrOfCustomer;

    private final Double passwordAverageLength;
    private final Integer passwordMinimumLength;
    private final Integer passwordMaximumLength;

    private final Double customerAverageAge;
    private final Integer customerYoungestAge;
    private final Integer customerOldestAge;

    public CustomerReportDto(Long nbrOfCustomer,
                             Double passwordAverageLength, Integer passwordMinimumLength, Integer passwordMaximumLength,
                             Double customerAverageAge, Integer customerYoungestAge, Integer customerOldestAge) {
        this.nbrOfCustomer = nbrOfCustomer;
        this.passwordAverageLength = passwordAverageLength;
        this.passwordMinimumLength = passwordMinimumLength;
        this.passwordMaximumLength = passwordMaximumLength;
        this.customerAverageAge = customerAverageAge;
        this.customerYoungestAge = customerYoungestAge;
        this.customerOldestAge = customerOldestAge;
    }

    public Long getNbrOfCustomer() {
        return nbrOfCustomer;
    }

    public Double getPasswordAverageLength() {
        return passwordAverageLength;
    }

    public Integer getPasswordMinimumLength() {
        return passwordMinimumLength;
    }

    public Integer getPasswordMaximumLength() {
        return passwordMaximumLength;
    }

    public Double getCustomerAverageAge() {
        return customerAverageAge;
    }

    public Integer getCustomerYoungestAge() {
        return customerYoungestAge;
    }

    public Integer getCustomerOldestAge() {
        return customerOldestAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerReportDto that = (CustomerReportDto) o;
        return Objects.equals(nbrOfCustomer, that.nbrOfCustomer) && Objects.equals(passwordAverageLength, that.passwordAverageLength) && Objects.equals(passwordMinimumLength, that.passwordMinimumLength) && Objects.equals(passwordMaximumLength, that.passwordMaximumLength) && Objects.equals(customerAverageAge, that.customerAverageAge) && Objects.equals(customerYoungestAge, that.customerYoungestAge) && Objects.equals(customerOldestAge, that.customerOldestAge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nbrOfCustomer, passwordAverageLength, passwordMinimumLength, passwordMaximumLength, customerAverageAge, customerYoungestAge, customerOldestAge);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("nbrOfCustomer", nbrOfCustomer)
                .append("passwordAverageLength", passwordAverageLength)
                .append("passwordMinimumLength", passwordMinimumLength)
                .append("passwordMaximumLength", passwordMaximumLength)
                .append("customerAverageAge", customerAverageAge)
                .append("customerYoungestAge", customerYoungestAge)
                .append("customerOldestAge", customerOldestAge)
                .toString();
    }
}