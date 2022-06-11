package be.cocoding.jpa.exercise.entity.customer;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {

    @Column(name = "STREET")
    private String streetName;

    @Column(name = "STREET_NBR")
    private String streetNumber;

    @Column(name = "CITY")
    private String city;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    public Address() {
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(streetName, address.streetName) && Objects.equals(streetNumber, address.streetNumber)
                && Objects.equals(city, address.city) && Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, streetNumber, city, zipCode);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("streetName", streetName)
                .append("streetNumber", streetNumber)
                .append("city", city)
                .append("zipCode", zipCode)
                .toString();
    }
}
