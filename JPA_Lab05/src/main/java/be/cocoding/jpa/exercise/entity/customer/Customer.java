package be.cocoding.jpa.exercise.entity.customer;

import be.cocoding.jpa.exercise.entity.order.Order;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "CUSTOMER")
@SequenceGenerator(name = "Customer.sequence", sequenceName = "CUSTOMER_SEQ", allocationSize = 1)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Customer.sequence")
    private Integer id;

    private String username;

    private String password;

    @Column(name = "LAST_NAME")
    private String lastname;

    @Column(name = "FIRST_NAME")
    private String firstname;

    @Column(name = "BIRTHDAY")
    @Temporal(DATE)
    private Date birthDate;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

    @Column(name = "CREATED_ON")
    private Timestamp createdOn;

    @Column(name = "UPDATED_ON")
    private Timestamp updatedOn;

    @OneToMany(mappedBy = "customer")
    private Set<CustomerAddress> addresses;

    @OneToMany(mappedBy = "customer")
    private Set<Payment> paymentMethods;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;

    public Customer() {
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Set<CustomerAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<CustomerAddress> addresses) {
        this.addresses = addresses;
    }

    public Set<Payment> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(Set<Payment> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(username, customer.username)
                && Objects.equals(password, customer.password) && Objects.equals(lastname, customer.lastname)
                && Objects.equals(firstname, customer.firstname) && Objects.equals(birthDate, customer.birthDate)
                && Objects.equals(country, customer.country) && Objects.equals(createdOn, customer.createdOn)
                && Objects.equals(updatedOn, customer.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, lastname, firstname, birthDate, country, createdOn, updatedOn);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("username", username)
                .append("password", password)
                .append("lastname", lastname)
                .append("firstname", firstname)
                .append("birthDate", birthDate)
//                .append("country", country)
                .append("createdOn", createdOn)
                .append("updatedOn", updatedOn)
                .toString();
    }
}
