package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.CustomerDao;
import be.cocoding.jpa.exercise.entity.customer.Customer;
import be.cocoding.jpa.exercise.test.SpringDaoContextTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerDaoIT extends SpringDaoContextTest {

    @Autowired
    private CustomerDao dao;

    @Test
    @Transactional
    public void testFindById() {
//        Customer actual = dao.findById(1);
//        assertNotNull(actual);
//        assertEquals("Anthony", actual.getUsername());
        //TODO Comparer les autres attributs également
    }

    @Test
    //@Transactional: Pour avoir une entité Detached dans le test,
    // il ne doit pas être transactionel.
    public void testDetachedLoadLazyAssociation(){
        //TODO Récupérer l'entité et essayer de charger l'association LAZY
    }
}