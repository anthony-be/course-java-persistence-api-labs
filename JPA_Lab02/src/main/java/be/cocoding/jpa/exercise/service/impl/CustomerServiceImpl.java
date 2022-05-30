package be.cocoding.jpa.exercise.service.impl;

import be.cocoding.jpa.exercise.entity.customer.Customer;
import be.cocoding.jpa.exercise.service.CustomerService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {


    @Override
    public Customer findById(int id) {
        throw new NotImplementedException("Method not implemented");
    }
}
