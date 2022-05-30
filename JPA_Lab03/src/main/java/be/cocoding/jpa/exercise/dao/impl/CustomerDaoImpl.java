package be.cocoding.jpa.exercise.dao.impl;

import be.cocoding.jpa.exercise.dao.CustomerDao;
import be.cocoding.jpa.exercise.entity.customer.Customer;
import be.cocoding.jpa.exercise.entity.customer.Country;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;

@Repository("customerDao")
public class CustomerDaoImpl implements CustomerDao {

    @Override
    public Customer findById(int id) {
        throw new NotImplementedException("Method not implemented");
    }


    private Country getCountryByCode(String isoCode){
//        TypedQuery<Country> query = em.createQuery("select co from Country co where co.isoCode = :isoCode", Country.class);
//        query.setParameter("isoCode", isoCode);
//        return query.getSingleResult();
        return null;
    }

}
