package com.synchrony.demo.controller;

import com.synchrony.demo.entity.Employee;
import com.synchrony.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    /**
     *
     * @param employee
     * @return
     */
    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody Employee employee) {
        return (Employee) demoService.saveEmployee(employee);
    }

    /**
     *
     * @return
     */
    @GetMapping("/employeeList")
    public List<Employee> getUserById() {
        return demoService.fetchEmployeeList();
    }

    /**
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        demoService.deleteEmployeeById(id);
    }

    /**
     *
     * @param email
     * @return
     */
    @PostMapping("/employee/emailId")
    public Employee getEmployeeByEmail(@RequestParam String email){
        return demoService.findByEmployeeEmail(email);
    }

}
