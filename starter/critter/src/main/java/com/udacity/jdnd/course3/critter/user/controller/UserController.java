package com.udacity.jdnd.course3.critter.user.controller;

import com.udacity.jdnd.course3.critter.user.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = userService.storeCustomer(convertCustomerDTOtoEntity(customerDTO));
        return convertCustomerEntityToDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        userService.fetchAllCustomers().forEach(s-> customerDTOS.add(convertCustomerEntityToDTO(s)));
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = userService.fetchCustomerByPet(petId);
        return convertCustomerEntityToDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = userService.storeEmployee(convertEmployeeDTOtoEntity(employeeDTO));
        return convertEmployeeEntityToDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = userService.fetchEmployee(employeeId);
        return convertEmployeeEntityToDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.updateEmployeeAvailability(employeeId, daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        userService.findEmployeesByAvailability(employeeDTO.getSkills(), employeeDTO.getDate())
                .forEach(s-> employeeDTOS.add(convertEmployeeEntityToDTO(s)));
        return employeeDTOS;
    }

    private Customer convertCustomerDTOtoEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        if (customerDTO != null) {
            customer.setName(customerDTO.getName());
            customer.setNotes(customerDTO.getNotes());
            customer.setPhoneNumber(customerDTO.getPhoneNumber());
        }
        return customer;
    }

    private Employee convertEmployeeDTOtoEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        if (employeeDTO != null) {
            employee.setName(employeeDTO.getName());
            employee.setSkills(employeeDTO.getSkills());
            employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        }
        return employee;
    }

    private CustomerDTO convertCustomerEntityToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        if (customer != null) {
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setNotes(customer.getNotes());
            customerDTO.setPetIds(customer.getPetIds());
            customerDTO.setPhoneNumber(customer.getPhoneNumber());
        }
        return customerDTO;
    }

    private EmployeeDTO convertEmployeeEntityToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        if (employee != null) {
            employeeDTO.setId(employee.getId());
            employeeDTO.setName(employee.getName());
            employeeDTO.setSkills(employee.getSkills());
            employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        }
        return employeeDTO;
    }

}
