package com.udacity.jdnd.course3.critter.user.dao;

import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("from Customer")
    List<Customer> findAllCustomers();

    @Query("from Customer where id=:id")
    Customer findCustomerById(long id);

    @Query("from Employee")
    List<Employee> findAllEmployees();

    @Query("from Employee where id=:id")
    Employee findEmployeeById(long id);
}
