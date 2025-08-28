package com.reliaquest.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.reliaquest.api.exception.EmployeeException;
import com.reliaquest.api.exception.EmployeeRuntimeException;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.repository.IEmployeeRepository;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link EmployeeService}.
 * <p>
 * Verifies service logic for employee operations using mocked repository.
 * Covers success and failure scenarios for all main service methods.
 *
 * @author skurade
 */
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private IEmployeeRepository repository;

    @InjectMocks
    private EmployeeService service;

    private Employee emp1, emp2, emp3;

    /**
     * Initializes test data before each test.
     */
    @BeforeEach
    void setUp() {
        emp1 = new Employee("1", "John", 1000, 30, "Dev", "john@company.com");
        emp2 = new Employee("2", "Jane", 2000, 28, "QA", "jane@company.com");
        emp3 = new Employee("3", "Jake", 3000, 35, "Lead", "jake@company.com");
    }

    /**
     * Tests successful retrieval of all employees.
     * Asserts that the returned list contains expected employees.
     */
    @Test
    void testGetAllEmployeesSuccess() throws EmployeeException {
        when(repository.getAll()).thenReturn(List.of(emp1, emp2));
        List<Employee> result = service.getAllEmployees();
        assertEquals(2, result.size());
    }

    /**
     * Tests exception handling when fetching all employees fails.
     * Expects an {@link EmployeeRuntimeException} to be thrown.
     */
    @Test
    void testGetAllEmployeesThrowsException() throws EmployeeException {
        when(repository.getAll()).thenThrow(new EmployeeException("API error"));
        assertThrows(EmployeeRuntimeException.class, () -> service.getAllEmployees());
    }

    /**
     * Tests searching employees by name fragment when matches are found.
     * Asserts that the returned list contains matching employees.
     */
    @Test
    void testSearchEmployeesByNameFound() throws EmployeeException {
        when(repository.getAll()).thenReturn(List.of(emp1, emp2, emp3));
        List<Employee> result = service.searchEmployeesByName("Ja");
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(e -> e.getName().equals("Jane")));
        assertTrue(result.stream().anyMatch(e -> e.getName().equals("Jake")));
    }

    /**
     * Tests searching employees by name fragment when no matches are found.
     * Asserts that the returned list is empty.
     */
    @Test
    void testSearchEmployeesByNameNotFound() throws EmployeeException {
        when(repository.getAll()).thenReturn(List.of(emp1, emp2));
        List<Employee> result = service.searchEmployeesByName("zzz");
        assertTrue(result.isEmpty());
    }

    /**
     * Tests exception handling when searching employees by name fails.
     * Expects an {@link EmployeeRuntimeException} to be thrown.
     */
    @Test
    void testSearchEmployeesByNameThrowsException() throws EmployeeException {
        when(repository.getAll()).thenThrow(new EmployeeException("API error"));
        assertThrows(EmployeeRuntimeException.class, () -> service.searchEmployeesByName("John"));
    }

    /**
     * Tests retrieval of an employee by ID when found.
     * Asserts that the returned employee matches the expected data.
     */
    @Test
    void testGetEmployeeByIdFound() throws EmployeeException {
        when(repository.getById("1")).thenReturn(Optional.of(emp1));
        Employee result = service.getEmployeeById("1");
        assertEquals("John", result.getName());
    }

    /**
     * Tests retrieval of an employee by ID when not found.
     * Expects an {@link EmployeeRuntimeException} to be thrown.
     */
    @Test
    void testGetEmployeeByIdNotFound() throws EmployeeException {
        when(repository.getById("2")).thenReturn(Optional.empty());
        assertThrows(EmployeeRuntimeException.class, () -> service.getEmployeeById("2"));
    }

    /**
     * Tests exception handling when fetching an employee by ID fails.
     * Expects an {@link EmployeeRuntimeException} to be thrown.
     */
    @Test
    void testGetEmployeeByIdThrowsException() throws EmployeeException {
        when(repository.getById("3")).thenThrow(new EmployeeException("API error"));
        assertThrows(EmployeeRuntimeException.class, () -> service.getEmployeeById("3"));
    }

    /**
     * Tests retrieval of the highest salary among employees.
     * Asserts that the returned salary is correct.
     */
    @Test
    void testGetHighestSalarySuccess() throws EmployeeException {
        when(repository.getAll()).thenReturn(List.of(emp1, emp2, emp3));
        int result = service.getHighestSalary();
        assertEquals(3000, result);
    }

    /**
     * Tests retrieval of the highest salary when the employee list is empty.
     * Asserts that the returned salary is zero.
     */
    @Test
    void testGetHighestSalaryEmptyList() throws EmployeeException {
        when(repository.getAll()).thenReturn(Collections.emptyList());
        int result = service.getHighestSalary();
        assertEquals(0, result);
    }

    /**
     * Tests exception handling when fetching the highest salary fails.
     * Expects an {@link EmployeeRuntimeException} to be thrown.
     */
    @Test
    void testGetHighestSalaryThrowsException() throws EmployeeException {
        when(repository.getAll()).thenThrow(new EmployeeException("API error"));
        assertThrows(EmployeeRuntimeException.class, () -> service.getHighestSalary());
    }

    /**
     * Tests retrieval of the top 10 highest earning employees.
     * Asserts that the returned list contains the correct employees.
     */
    @Test
    void testGetTop10HighestEarningEmployeeNamesSuccess() throws EmployeeException {
        List<Employee> employees = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            employees.add(new Employee(
                    String.valueOf(i), "Emp" + i, 1000 + i * 100, 25 + i, "Title", "emp" + i + "@company.com"));
        }
        when(repository.getAll()).thenReturn(employees);
        List<String> result = service.getTop10HighestEarningEmployeeNames();
        assertEquals(10, result.size());
        assertEquals("Emp15", result.get(0));
    }

    /**
     * Tests exception handling when fetching top 10 highest earning employees fails.
     * Expects an {@link EmployeeRuntimeException} to be thrown.
     */
    @Test
    void testGetTop10HighestEarningEmployeeNamesThrowsException() throws EmployeeException {
        when(repository.getAll()).thenThrow(new EmployeeException("API error"));
        assertThrows(EmployeeRuntimeException.class, () -> service.getTop10HighestEarningEmployeeNames());
    }

    /**
     * Tests successful creation of a new employee.
     * Asserts that the returned employee matches the expected data.
     */
    @Test
    void testCreateEmployeeSuccess() throws EmployeeException {
        Map<String, Object> empMap = getEmpMap();
        Employee newEmp = new Employee(null, "New", 5000, 40, "Mgr", null);
        when(repository.create(any())).thenReturn(emp1);
        Employee result = service.createEmployee(empMap);
        assertEquals("John", result.getName());
    }

    private static Map<String, Object> getEmpMap() {
        Map<String, Object> empMap = new HashMap<>();
        empMap.put("name", "New");
        empMap.put("salary", 5000);
        empMap.put("age", 40);
        empMap.put("title", "Mgr");
        return empMap;
    }

    /**
     * Tests exception handling when employee creation fails.
     * Expects an {@link EmployeeRuntimeException} to be thrown.
     */
    @Test
    void testCreateEmployeeThrowsException() throws EmployeeException {
        Map<String, Object> empMap = getEmpMap();
        when(repository.create(any())).thenThrow(new EmployeeException("API error"));
        assertThrows(EmployeeRuntimeException.class, () -> service.createEmployee(empMap));
    }

    /**
     * Tests successful deletion of an employee by ID.
     * Asserts that the returned name matches the expected employee.
     */
    @Test
    void testDeleteEmployeeByIdSuccess() throws EmployeeException {
        when(repository.getById("1")).thenReturn(Optional.of(emp1));
        when(repository.deleteById("1")).thenReturn(true);
        String result = service.deleteEmployeeById("1");
        assertEquals("John", result);
    }

    /**
     * Tests deletion of an employee by ID when not found or not deleted.
     * Expects an {@link EmployeeRuntimeException} to be thrown.
     */
    @Test
    void testDeleteEmployeeByIdNotFound() throws EmployeeException {
        when(repository.getById("2")).thenReturn(Optional.of(emp2));
        when(repository.deleteById("2")).thenReturn(false);
        assertThrows(EmployeeRuntimeException.class, () -> service.deleteEmployeeById("2"));
    }

    /**
     * Tests exception handling when employee deletion fails.
     * Expects an {@link EmployeeRuntimeException} to be thrown.
     */
    @Test
    void testDeleteEmployeeByIdThrowsException() throws EmployeeException {
        when(repository.getById("3")).thenReturn(Optional.of(emp3));
        when(repository.deleteById("3")).thenThrow(new EmployeeException("API error"));
        assertThrows(EmployeeRuntimeException.class, () -> service.deleteEmployeeById("3"));
    }
}
