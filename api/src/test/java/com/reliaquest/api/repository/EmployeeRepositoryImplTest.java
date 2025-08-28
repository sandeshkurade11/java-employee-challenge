package com.reliaquest.api.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.reliaquest.api.constants.ApiConstants;
import com.reliaquest.api.exception.EmployeeException;
import com.reliaquest.api.model.Employee;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Unit tests for {@link EmployeeRepositoryImpl}.
 * <p>
 * Verifies repository methods for employee CRUD operations using mocked RestTemplate.
 * Covers success and failure scenarios for all main methods.
 *
 * @author skurade
 */
@ExtendWith(MockitoExtension.class)
class EmployeeRepositoryImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeRepositoryImpl repository;

    /**
     * Tests successful retrieval of all employees.
     * Asserts that the returned list contains expected employee data.
     */
    @Test
    void testGetAllSuccess() throws EmployeeException {
        Map<String, Object> empMap = new HashMap<>();
        empMap.put("id", "1");
        empMap.put("employee_name", "John");
        empMap.put("employee_salary", 1000);
        empMap.put("employee_age", 30);
        empMap.put("employee_title", "Dev");
        empMap.put("employee_email", "john@company.com");
        List<Map<String, Object>> data = List.of(empMap);
        Map<String, Object> responseMap = Map.of(ApiConstants.DATA, data);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);

        when(restTemplate.getForEntity(ApiConstants.BASE_URL, Map.class)).thenReturn(responseEntity);

        List<Employee> result = repository.getAll();
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getName());
    }

    /**
     * Tests exception handling when fetching all employees fails.
     * Expects an {@link EmployeeException} to be thrown.
     */
    @Test
    void testGetAllThrowsException() {
        when(restTemplate.getForEntity(ApiConstants.BASE_URL, Map.class)).thenThrow(new RuntimeException("API error"));
        assertThrows(EmployeeException.class, () -> repository.getAll());
    }

    /**
     * Tests successful retrieval of an employee by ID.
     * Asserts that the returned Optional contains the expected employee.
     */
    @Test
    void testGetByIdFound() throws EmployeeException {
        Map<String, Object> empMap = new HashMap<>();
        empMap.put("id", "1");
        empMap.put("employee_name", "John");
        empMap.put("employee_salary", 1000);
        empMap.put("employee_age", 30);
        empMap.put("employee_title", "Dev");
        empMap.put("employee_email", "john@company.com");
        Map<String, Object> responseMap = Map.of(ApiConstants.DATA, empMap);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);

        when(restTemplate.getForEntity(ApiConstants.BASE_URL + ApiConstants.SLASH + "1", Map.class))
                .thenReturn(responseEntity);

        Optional<Employee> result = repository.getById("1");
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
    }

    /**
     * Tests retrieval of an employee by ID when not found.
     * Asserts that the returned Optional is empty.
     */
    @Test
    void testGetByIdNotFound() throws EmployeeException {
        when(restTemplate.getForEntity(ApiConstants.BASE_URL + ApiConstants.SLASH + "2", Map.class))
                .thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(404), null, null, null));
        Optional<Employee> result = repository.getById("2");
        assertTrue(result.isEmpty());
    }

    /**
     * Tests exception handling when fetching employee by ID fails.
     * Expects an {@link EmployeeException} to be thrown.
     */
    @Test
    void testGetByIdThrowsException() {
        when(restTemplate.getForEntity(anyString(), eq(Map.class))).thenThrow(new RuntimeException("API error"));
        assertThrows(EmployeeException.class, () -> repository.getById("3"));
    }

    /**
     * Tests successful creation of a new employee.
     * Asserts that the returned employee contains expected data.
     */
    @Test
    void testCreateSuccess() throws EmployeeException {
        Employee emp = new Employee(null, "Jane", 2000, 28, "QA", null);
        Map<String, Object> empMap = new HashMap<>();
        empMap.put("id", "2");
        empMap.put("employee_name", "Jane");
        empMap.put("employee_salary", 2000);
        empMap.put("employee_age", 28);
        empMap.put("employee_title", "QA");
        empMap.put("employee_email", "jane@company.com");
        Map<String, Object> responseMap = Map.of(ApiConstants.DATA, empMap);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);

        when(restTemplate.postForEntity(eq(ApiConstants.BASE_URL), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(responseEntity);

        Employee result = repository.create(emp);
        assertEquals("Jane", result.getName());
        assertEquals(2000, result.getSalary());
    }

    /**
     * Tests exception handling when employee creation fails.
     * Expects an {@link EmployeeException} to be thrown.
     */
    @Test
    void testCreateThrowsException() {
        Employee emp = new Employee(null, "Jane", 2000, 28, "QA", null);
        when(restTemplate.postForEntity(eq(ApiConstants.BASE_URL), any(HttpEntity.class), eq(Map.class)))
                .thenThrow(new RuntimeException("API error"));
        assertThrows(EmployeeException.class, () -> repository.create(emp));
    }

    /**
     * Tests successful deletion of an employee by ID.
     * Asserts that the result is true.
     */
    @Test
    void testDeleteByIdSuccess() throws EmployeeException {
        ResponseEntity<Map> getByIdResponse = getMapResponseEntity();
        when(restTemplate.getForEntity(ApiConstants.BASE_URL + ApiConstants.SLASH + "1", Map.class))
                .thenReturn(getByIdResponse);
        Map<String, Object> deleteResponseMap = Map.of(ApiConstants.DATA, true);
        ResponseEntity<Map> deleteResponse = new ResponseEntity<>(deleteResponseMap, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(ApiConstants.BASE_URL),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(Map.class)))
                .thenReturn(deleteResponse);

        boolean result = repository.deleteById("1");
        assertTrue(result);
    }

    private static ResponseEntity<Map> getMapResponseEntity() {
        Map<String, Object> empMap = new HashMap<>();
        empMap.put("id", "1");
        empMap.put("employee_name", "John");
        empMap.put("employee_salary", 1000);
        empMap.put("employee_age", 30);
        empMap.put("employee_title", "Dev");
        empMap.put("employee_email", "john@company.com");
        Map<String, Object> responseMap = Map.of(ApiConstants.DATA, empMap);
        ResponseEntity<Map> getByIdResponse = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return getByIdResponse;
    }

    /**
     * Tests exception handling when employee deletion fails.
     * Expects an {@link EmployeeException} to be thrown.
     */
    @Test
    void testDeleteByIdThrowsException() {
        ResponseEntity<Map> getByIdResponse = getMapResponseEntity();
        when(restTemplate.getForEntity(ApiConstants.BASE_URL + ApiConstants.SLASH + "1", Map.class))
                .thenReturn(getByIdResponse);
        when(restTemplate.exchange(
                eq(ApiConstants.BASE_URL),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(Map.class)))
                .thenThrow(new RuntimeException("API error"));

        assertThrows(EmployeeException.class, () -> repository.deleteById("3"));
    }
}
