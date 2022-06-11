package be.cocoding.jpa.exercise.service;

import be.cocoding.jpa.exercise.entity.customer.Customer;

public interface CustomerService {

    Customer findById(int id);

}
