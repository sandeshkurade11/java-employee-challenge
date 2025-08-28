package com.reliaquest.api.controller.employee;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.IEmployeeService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

/**
 * Unit tests for {@link EmployeeController}.
 * <p>
 * Verifies controller endpoints for employee operations using mocked service.
 * Uses Mockito for dependency injection and behavior simulation.
 *
 * @author skurade
 */
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private IEmployeeService service;

    @InjectMocks
    private EmployeeController controller;

    private List<Employee> createEmployeeList() {
        return Arrays.asList(
                new Employee("1", "John", 1000, 30, "Dev", "john@company.com"),
                new Employee("2", "Jane", 2000, 28, "QA", "jane@company.com"),
                new Employee("3", "Bob", 1500, 35, "Manager", "bob@company.com"),
                new Employee("4", "Alice", 3000, 40, "Lead", "alice@company.com"));
    }

    /**
     * Tests retrieval of all employees.
     * Asserts that the response contains the expected list and status code.
     */
    @Test
    void testGetAllEmployees() {
        List<Employee> employees = createEmployeeList();
        when(service.getAllEmployees()).thenReturn(employees);
        ResponseEntity<List<Employee>> response = controller.getAllEmployees();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(employees, response.getBody());
    }

    /**
     * Tests searching employees by name.
     * Asserts that the response contains the expected list and status code.
     */
    @Test
    void testGetEmployeesByNameSearch() {
        List<Employee> employees = createEmployeeList();
        when(service.searchEmployeesByName("Jane")).thenReturn(employees);
        ResponseEntity<List<Employee>> response = controller.getEmployeesByNameSearch("Jane");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(employees, response.getBody());
    }

    /**
     * Tests retrieval of an employee by ID when found.
     * Asserts that the response contains the expected employee and status code.
     */
    @Test
    void testGetEmployeeById_Found() {
        Employee emp = new Employee("3", "Bob", 1500, 35, "Manager", "bob@company.com");
        when(service.getEmployeeById("3")).thenReturn(emp);
        ResponseEntity<Employee> response = controller.getEmployeeById("3");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(emp, response.getBody());
    }

    /**
     * Tests retrieval of an employee by ID when not found.
     * Asserts that the response status code is 404 and body is null.
     */
    @Test
    void testGetEmployeeById_NotFound() {
        when(service.getEmployeeById("99")).thenReturn(null);
        ResponseEntity<Employee> response = controller.getEmployeeById("99");
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    /**
     * Tests retrieval of the highest salary among employees.
     * Asserts that the response contains the expected salary and status code.
     */
    @Test
    void testGetHighestSalaryOfEmployees() {
        when(service.getHighestSalary()).thenReturn(5000);
        ResponseEntity<Integer> response = controller.getHighestSalaryOfEmployees();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(5000, response.getBody());
    }

    /**
     * Tests retrieval of the top ten highest earning employees.
     * Asserts that the response contains the expected list and status code.
     */
    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        List<String> topEmployeeNames = Arrays.asList("Alice", "Jane", "Bob", "John");
        when(service.getTop10HighestEarningEmployeeNames()).thenReturn(topEmployeeNames);
        ResponseEntity<List<String>> response = controller.getTopTenHighestEarningEmployeeNames();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(topEmployeeNames, response.getBody());
    }

    /**
     * Tests creation of a new employee.
     * Asserts that the response contains the created employee and status code.
     */
    @Test
    void testCreateEmployee() {
        Employee input = new Employee(null, "Eve", 2500, 29, "DevOps", null);
        Employee created = new Employee("5", "Eve", 2500, 29, "DevOps", "eve@company.com");
        when(service.createEmployee(any(Employee.class))).thenReturn(created);
        ResponseEntity<Object> response = controller.createEmployee(input);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(created, response.getBody());
    }

    /**
     * Tests deletion of an employee by ID when found.
     * Asserts that the response contains the employee name and status code.
     */
    @Test
    void testDeleteEmployeeById_Found() {
        when(service.deleteEmployeeById("5")).thenReturn("Eve");
        ResponseEntity<String> response = controller.deleteEmployeeById("5");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Eve", response.getBody());
    }

    /**
     * Tests deletion of an employee by ID when not found.
     * Asserts that the response status code is 404 and body is null.
     */
    @Test
    void testDeleteEmployeeById_NotFound() {
        when(service.deleteEmployeeById("99")).thenReturn(null);
        ResponseEntity<String> response = controller.deleteEmployeeById("99");
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
