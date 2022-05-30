package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.CustomerDao;
import be.cocoding.jpa.exercise.entity.customer.AddressType;
import be.cocoding.jpa.exercise.entity.customer.Customer;
import be.cocoding.jpa.exercise.entity.customer.CustomerAddress;
import be.cocoding.jpa.exercise.entity.customer.Payment;
import be.cocoding.jpa.exercise.test.SpringDaoContextTest;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Set;

import static org.junit.Assert.*;

public class CustomerDaoIT extends SpringDaoContextTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CustomerDao dao;

    @Test
    @Transactional
    public void testFindById() throws ParseException {
        Customer actual = dao.findById(1);
        assertNotNull(actual);
        assertEquals("Anthony", actual.getUsername());
        assertEquals("dummy", actual.getPassword());
        assertEquals("Feron", actual.getLastname());
        assertEquals("Anthony", actual.getFirstname());
        assertEquals(DateUtils.parseDate("1983-01-01", "yyyy-MM-dd"), actual.getBirthDate());
    }

    @Test(expected = LazyInitializationException.class)
    //@Transactional: Pour avoir une entité Detached dans le test,
    // il ne doit pas être transactionel.
    public void testDetachedLoadLazyAssociation(){
        //Récupérer l'entité et essayer de charger une association LAZY

        Customer actual = dao.findById(1);
        assertNotNull(actual);
        Set<CustomerAddress> addresses = actual.getAddresses(); // Ne génère pas le chargement de l'association. Renvoi un proxy Hibernate
        addresses.size();// L'appel à size() a besoin de charger l'association, pour compter le nombre d'éléments
    }

    @Test
    @Transactional
    public void testCreateCustomer() throws Exception {
        Customer actual = dao.createCustomer("test", "password", "Test lastname", "Test firstname",
                DateUtils.parseDate("1980-05-20", "yyyy-MM-dd"), "BE");
        assertNotNull(actual);

        actual = dao.findById(actual.getId());
        assertNotNull(actual);
        assertEquals("test", actual.getUsername());
    }

    @Test
    @Transactional
    public void testChangeCustomerPassword() {
        Customer customer = dao.findById(1);

        Customer actual = dao.changeCustomerPassword(customer, "newPassword");
        assertNotNull(actual);

        actual = dao.findById(1);
        assertEquals("newPassword", actual.getPassword());
    }

    @Test
    @Transactional
    public void testAddPaymentMethod() throws ParseException {
        final int custId = 1;

        Payment payment = dao.addPaymentMethod(custId, "Payconiq", "123-456789-00",
                DateUtils.parseDate("2025-01","yyyy-MM"));
        assertNotNull(payment);

        Customer customer = dao.findById(custId);
        assertTrue(customer.getPaymentMethods().contains(payment));
    }

    @Test
    @Transactional
    public void testAddAddress() {
        final int custId = 1;

        CustomerAddress actualAddress = dao.addAddress(custId, AddressType.DELIVERY, "Rue des choux", "55", "1000",
                "Bruxelles", "BE", "022/12345678", "0470/123456");
        assertNotNull(actualAddress);

        Customer customer = em.find(Customer.class, custId);
        assertNotNull(customer);
        assertTrue(customer.getAddresses().contains(actualAddress));
    }

    @Test
    @Transactional
    public void resetCustomerPassword() {
        final int custId = 5;

        dao.resetCustomerPassword(custId);

        Customer customer = dao.findById(custId);
        assertNotNull(customer);
        assertEquals("12345", customer.getPassword());
    }
}