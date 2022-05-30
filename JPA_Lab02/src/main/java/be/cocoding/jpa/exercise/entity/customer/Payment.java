package be.cocoding.jpa.exercise.entity.customer;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

public class Payment {

    private Integer id;

    private Customer customer;

    private String provider;

    private String accountNr;

    private Date expirationDate;

    public Payment() {
    }

    public Integer getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getAccountNr() {
        return accountNr;
    }

    public void setAccountNr(String accountNr) {
        this.accountNr = accountNr;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) && Objects.equals(customer, payment.customer)
                && Objects.equals(provider, payment.provider) && Objects.equals(accountNr, payment.accountNr)
                && Objects.equals(expirationDate, payment.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, provider, accountNr, expirationDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
//                .append("customer", customer)
                .append("provider", provider)
                .append("accountNr", accountNr)
                .append("expirationDate", expirationDate)
                .toString();
    }
}
