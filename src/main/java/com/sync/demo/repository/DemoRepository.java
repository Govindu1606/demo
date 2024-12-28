package com.sync.demo.repository;

import com.sync.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface DemoRepository extends JpaRepository<Employee, Long> {

    Employee findByEmployeeEmail(String employeeEmail);
}
