package be.cocoding.jpa.exercise.entity.customer;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class CustomerAddress {

    private Integer id;

    private Customer customer;

    private Address address;

    private Country country;

    private String phone;

    private String mobile;

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
