package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.exceptions.UserNotFoundException;
import com.udacity.jdnd.course3.critter.user.dao.UserRepository;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.model.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Customer storeCustomer(Customer customer) {
       return userRepository.save(customer);
    }

    @Transactional
    public Customer fetchCustomerByPet(long petId) throws UserNotFoundException {
        return userRepository.findAllCustomers().stream()
                .filter(c -> c.getPetIds().contains(petId))
                .findAny()
                .orElseThrow(() -> new UserNotFoundException("Customer with pet with id: " + petId + " not Found!"));
    }

    @Transactional
    public List<Customer> fetchAllCustomers() {
        return userRepository.findAllCustomers();
    }

    @Transactional
    public Employee storeEmployee(Employee employee) {
        return userRepository.save(employee);
    }

    @Transactional
    public Customer fetchCustomer(long customerId) {
        Customer customer = userRepository.findCustomerById(customerId);
        if (customer == null) throw new UserNotFoundException("Customer with id: " + customerId + " not Found!");
        return customer;
    }

    @Transactional
    public Employee fetchEmployee(long employeeId) throws UserNotFoundException {
        Employee employee = userRepository.findEmployeeById(employeeId);
        if (employee == null) throw new UserNotFoundException("Employee with id: " + employeeId + " not Found!");
        return employee;
    }

    @Transactional
    public void updateEmployeeAvailability(long id, Set<DayOfWeek> days) {
        Employee employee = fetchEmployee(id);
        if (employee != null) {
            employee.setDaysAvailable(days);
            userRepository.save(employee);
        }
    }

    @Transactional
    public List<Employee> findEmployeesByAvailability(Set<EmployeeSkill> skills, LocalDate date) {
       return userRepository.findAllEmployees().stream()
               .filter(e -> e.getDaysAvailable().contains(DayOfWeek.from(date)) && e.getSkills().containsAll(skills))
               .collect(Collectors.toList());
    }
}
