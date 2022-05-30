package be.cocoding.jpa.exercise.entity.customer;

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

public class Customer {

    private Integer id;

    private String username;

    private String password;

    private String lastname;

    private String firstname;

    private Date birthDate;

    private Country country;

    private Timestamp createdOn;

    private Timestamp updatedOn;

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
