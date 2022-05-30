package be.cocoding.jpa.exercise.dao;

import be.cocoding.jpa.exercise.entity.customer.Customer;

public interface CustomerDao {

    Customer findById(int id);

}
