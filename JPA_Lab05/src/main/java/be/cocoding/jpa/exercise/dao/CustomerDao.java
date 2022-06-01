package be.cocoding.jpa.exercise.dao;

import be.cocoding.jpa.exercise.entity.customer.*;

import java.util.Collection;
import java.util.Date;

public interface CustomerDao {

    Customer findById(int custId);

    Customer createCustomer(String username, String password, String lastname, String firstname, Date birthday, String countryIsoCode);

    Customer changeCustomerPassword( Customer cust, String newPassword );

    Payment addPaymentMethod(int custId, String providerName, String accountNbr, Date expirationDate );

    CustomerAddress addAddress(int custId, AddressType type, String street, String number, String zipcode, String city, String countryIsoCode, String phone, String mobile );

    void resetCustomerPassword( int custId );

    Collection<Customer> findAll();

    Collection<Customer> findAll(int page, int pageSize);

    Customer findByUsername(String username);
}
