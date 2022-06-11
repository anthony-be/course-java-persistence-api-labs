package be.cocoding.jpa.exercise.entity.customer;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CUST_ADDRESS")
@SequenceGenerator(name = "CustomerAddress.sequence", sequenceName = "CUST_ADDRESS_SEQ", allocationSize = 1)
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CustomerAddress.sequence")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name="COUNTRY_ID")
    private Country country;

    @Basic
    private String phone;

    @Basic
    private String mobile;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private AddressType type;

    public CustomerAddress() {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public AddressType getType() {
        return type;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerAddress that = (CustomerAddress) o;
        return Objects.equals(id, that.id) && Objects.equals(customer, that.customer)
                && Objects.equals(address, that.address) && Objects.equals(country, that.country)
                && Objects.equals(phone, that.phone) && Objects.equals(mobile, that.mobile)
                && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, address, country, phone, mobile, type);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
//                .append("customer", customer)
                .append("address", address)
//                .append("country", country)
                .append("phone", phone)
                .append("mobile", mobile)
                .append("type", type)
                .toString();
    }
}
