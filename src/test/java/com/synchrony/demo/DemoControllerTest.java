package com.synchrony.demo;

import com.synchrony.demo.controller.DemoController;
import com.synchrony.demo.entity.Employee;
import com.synchrony.demo.repository.DemoRepository;
import com.synchrony.demo.service.DemoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static reactor.core.publisher.Mono.when;

@WebMvcTest(DemoController.class)
public class DemoControllerTest {

        @Mock
        private DemoService demoService;

        @Mock
        private DemoRepository demoRepository;

        @InjectMocks
        private DemoController demoController;

        private MockMvc mockMvc;

        @BeforeEach
        void setUp() {
            mockMvc = MockMvcBuilders.standaloneSetup(demoController).build();
        }

     @Test
     void testCreateEmployee() {
            Employee employee = Employee.builder().employeeEmail("test.employee@gmail.com").employeeId(1L).employeeName("Test").employeePortfolio("Portfolio1").build();
            when(demoService.saveEmployee(employee)).thenReturn(employee);
            when((Publisher<?>) demoRepository.save(employee)).thenReturn(employee);

            Employee employee1 = demoController.createEmployee(employee);
            assertEquals(employee1.getEmployeeName(), employee.getEmployeeName());
     }
}
