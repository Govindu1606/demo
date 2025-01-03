package com.sync.demo.serviceImpl;

import com.sync.demo.entity.Employee;
import com.sync.demo.repository.DemoRepository;
import com.sync.demo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoRepository demoRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);


    /**
     * @param Employee
     * @return
     */
    @Override
    @Cacheable(value = "employees", key = "#employee.employeeEmail")
    public Publisher<?> saveEmployee(Employee Employee) {
        return (Publisher<?>) demoRepository.save(Employee);
    }

    /**
     *
     * @return
     */
    @Override
    public List<Employee> fetchEmployeeList() {

        try {
            List<Long> employeeIds = demoRepository.findAll()
                    .stream()
                    .map(Employee::getEmployeeId)
                    .toList();

            List<Future<Employee>> futures = employeeIds.stream()
                    .map(id -> executorService.submit(() -> demoRepository.findById(id).orElse(null)))
                    .toList();

            return futures.stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException("Error retrieving employee details", e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Exception occurred while fetching the list, exception message :{}", e.getMessage());
        }
        return  null;
    }


    /**
     *
     */
    public void shutdownExecutor() {
        executorService.shutdown();
    }

    /**
     *
     * @param employeeId
     */
    @Override
    public void deleteEmployeeById(Long employeeId) {
        boolean isEmployeeIdExists = demoRepository.existsById(employeeId);
        if(isEmployeeIdExists) {
            demoRepository.deleteById(employeeId);
        }
    }

    /**
     *
     * @param emailId
     * @return
     */
    @Override
    @Cacheable(value = "employees", key = "#emailId")
    public Employee findByEmployeeEmail(String emailId) {
        return demoRepository.findByEmployeeEmail(emailId);
    }


}
