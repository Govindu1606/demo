package com.synchrony.demo.service;

import com.synchrony.demo.entity.Employee;
import org.reactivestreams.Publisher;

import java.util.List;

public interface DemoService {

    // Save operation
    Publisher<?> saveEmployee(Employee employee);

    // Read operation
    List<Employee> fetchEmployeeList();

    // Delete operation
    void deleteEmployeeById(Long employeeId);

    // Find Employee By Email
    Employee findByEmployeeEmail(String emailId);
}
