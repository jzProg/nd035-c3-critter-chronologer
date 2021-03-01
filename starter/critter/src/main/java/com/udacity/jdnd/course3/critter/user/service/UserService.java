package com.udacity.jdnd.course3.critter.user.service;

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

    public Customer fetchCustomerByPet(long petId) {
        return userRepository.findAllCustomers().stream()
                .filter(c -> c.getPetIds().contains(petId))
                .findAny()
                .orElse(null);
    }

    public List<Customer> fetchAllCustomers() {
        return userRepository.findAllCustomers();
    }

    @Transactional
    public Employee storeEmployee(Employee employee) {
        return userRepository.save(employee);
    }

    public Customer fetchCustomer(long customerId) {
        return userRepository.findCustomerById(customerId);
    }

    public Employee fetchEmployee(long employeeId) {
        return userRepository.findEmployeeById(employeeId);
    }

    @Transactional
    public void updateEmployeeAvailability(long id, Set<DayOfWeek> days) {
        Employee employee = fetchEmployee(id);
        employee.setDaysAvailable(days);
        userRepository.save(employee);
    }

    public List<Employee> findEmployeesByAvailability(Set<EmployeeSkill> skills, LocalDate date) {
       return userRepository.findAllEmployees().stream()
               .filter(e -> e.getDaysAvailable().contains(DayOfWeek.from(date)) && e.getSkills().containsAll(skills))
               .collect(Collectors.toList());
    }
}
