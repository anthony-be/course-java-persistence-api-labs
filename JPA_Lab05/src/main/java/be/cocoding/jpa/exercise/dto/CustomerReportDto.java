package be.cocoding.jpa.exercise.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class CustomerReportDto {

    private final Integer nbrOfCustomer;

    private final Double passwordAverageLength;
    private final Double passwordMinimumLength;
    private final Double passwordMaximumLength;

    private final Double customerAverageAge;
    private final Double customerYoungestAge;
    private final Double customerOldestAge;

    public CustomerReportDto(Integer nbrOfCustomer,
                             Double passwordAverageLength, Double passwordMinimumLength, Double passwordMaximumLength,
                             Double customerAverageAge, Double customerYoungestAge, Double customerOldestAge) {
        this.nbrOfCustomer = nbrOfCustomer;
        this.passwordAverageLength = passwordAverageLength;
        this.passwordMinimumLength = passwordMinimumLength;
        this.passwordMaximumLength = passwordMaximumLength;
        this.customerAverageAge = customerAverageAge;
        this.customerYoungestAge = customerYoungestAge;
        this.customerOldestAge = customerOldestAge;
    }

    public Integer getNbrOfCustomer() {
        return nbrOfCustomer;
    }

    public Double getPasswordAverageLength() {
        return passwordAverageLength;
    }

    public Double getPasswordMinimumLength() {
        return passwordMinimumLength;
    }

    public Double getPasswordMaximumLength() {
        return passwordMaximumLength;
    }

    public Double getCustomerAverageAge() {
        return customerAverageAge;
    }

    public Double getCustomerYoungestAge() {
        return customerYoungestAge;
    }

    public Double getCustomerOldestAge() {
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
