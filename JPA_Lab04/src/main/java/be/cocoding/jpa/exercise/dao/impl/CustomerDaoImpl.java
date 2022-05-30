package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.CustomerDao;
import be.cocoding.jpa.exercise.entity.customer.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Date;

@Repository("customerDao")
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public Customer findById(int custId) {
        return em.find(Customer.class, custId);
    }

    @Override
    public Customer createCustomer(String username, String password, String lastname, String firstname, Date birthday, String countryIsoCode) {
        Customer cust = new Customer();
        cust.setUsername(username);
        cust.setPassword(password);
        cust.setLastname(lastname);
        cust.setFirstname(firstname);
        cust.setBirthDate(birthday);
        cust.setCountry(getCountryByCode(countryIsoCode));
        em.persist(cust);
        return cust;
    }

    private Country getCountryByCode(String isoCode){
        TypedQuery<Country> query = em.createQuery("select co from Country co where co.isoCode = :isoCode", Country.class);
        query.setParameter("isoCode", isoCode);
        return query.getSingleResult();
    }

    @Override
    public Customer changeCustomerPassword(Customer cust, String newPassword) {
        Customer managed = findById(cust.getId());
        managed.setPassword(newPassword);
        // Pas besoin d'appeler la méthode em.merge(managed)
        // Comme l'entité est Managed, elle est est surveillée par EntityManager
        // A la fin de la transaction ou au flush, EntityManager fera la synchronisation db et fera le Update en db
        return managed;
    }

    @Override
    public Payment addPaymentMethod(int custId, String providerName, String accountNbr, Date expirationDate) {
        Customer customer = findById(custId);

        Payment payment = new Payment();
        payment.setProvider(providerName);
        payment.setAccountNr(accountNbr);
        payment.setExpirationDate(expirationDate);
        payment.setCustomer(customer);
        em.persist(payment);

        customer.getPaymentMethods().add(payment);

        return payment;
    }

    @Override
    public CustomerAddress addAddress(int custId, AddressType type, String street, String number, String zipcode, String city, String countryIsoCode, String phone, String mobile) {
        Customer customer = findById(custId);

        CustomerAddress custAddr = new CustomerAddress();
        custAddr.setType(type);
        Address addr = new Address();
        addr.setStreetName(street);
        addr.setStreetNumber(number);
        addr.setZipCode(zipcode);
        addr.setCity(city);
        custAddr.setAddress(addr);
        custAddr.setCountry(getCountryByCode(countryIsoCode));
        custAddr.setPhone(phone);
        custAddr.setMobile(mobile);

        custAddr.setCustomer(customer);
        em.persist(custAddr);

        customer.getAddresses().add(custAddr);

        return custAddr;
    }

    @Override
    public void resetCustomerPassword(int custId) {
        EntityManager entityManager = emf.createEntityManager();
        // Entity Manager doit gérer lui-même la transaction.
        // Normalement, c'est Spring qui le fait avec @PersistenceContext mais pas avec @PersistenceUnit
        try {
            entityManager.getTransaction().begin();
            Customer customer = entityManager.find(Customer.class, custId);
            customer.setPassword("12345");
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
