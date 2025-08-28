package com.reliaquest.api.repository;

import com.reliaquest.api.constants.ApiConstants;
import com.reliaquest.api.exception.EmployeeException;
import com.reliaquest.api.exception.EmployeeRuntimeException;
import com.reliaquest.api.model.Employee;
import java.util.*;
import java.util.stream.Collectors;

import com.reliaquest.api.model.DeleteMockEmployeeInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link IEmployeeRepository} for interacting with the Mock Employee API.
 * Handles CRUD operations for Employee entities via HTTP requests.
 *
 * @author skurade
 */
@Repository
public class EmployeeRepositoryImpl implements IEmployeeRepository {

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeRepositoryImpl.class);

    /**
     * Constructs an {@link EmployeeRepositoryImpl} with the provided {@link RestTemplate}.
     *
     * @param restTemplate the RestTemplate used for HTTP requests
     */
    public EmployeeRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves all employees from the Mock Employee API.
     *
     * @return a list of {@link Employee} objects, or an empty list if an error occurs
     * @throws EmployeeException if fetching employees fails
     */
    @Override
    public List<Employee> getAll() throws EmployeeException {
        logger.info("Fetching all employees from Mock API");
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(ApiConstants.BASE_URL, Map.class);
            List<Map<String, Object>> data =
                    (List<Map<String, Object>>) response.getBody().get(ApiConstants.DATA);
            return data.stream().map(this::mapToEmployee).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching all employees", e);
            throw new EmployeeException("Error fetching all employees", e);
        }
    }

    /**
     * Retrieves an employee by their unique identifier from the Mock Employee API.
     *
     * @param id the unique identifier of the employee
     * @return an {@link Optional} containing the {@link Employee} if found, or empty if not found or error occurs
     * @throws EmployeeException if fetching employee fails
     */
    @Override
    public Optional<Employee> getById(String id) throws EmployeeException {
        logger.info("Fetching employee by id: {}", id);
        try {
            ResponseEntity<Map> response =
                    restTemplate.getForEntity(ApiConstants.BASE_URL + ApiConstants.SLASH + id, Map.class);
            Map<String, Object> data = (Map<String, Object>) response.getBody().get(ApiConstants.DATA);
            return Optional.ofNullable(mapToEmployee(data));
        } catch (HttpClientErrorException e) {
            logger.warn("Employee not found for id: {}", id);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error fetching employee by id", e);
            throw new EmployeeException("Error fetching employee by id", e);
        }
    }

    /**
     * Creates a new employee in the Mock Employee API.
     *
     * @param employee the {@link Employee} object to create
     * @return the created {@link Employee} object
     * @throws EmployeeRuntimeException if creation fails
     */
    @Override
    public Employee create(Employee employee) throws EmployeeException {
        logger.info("Creating employee: {}", employee.getName());
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("name", employee.getName());
            request.put("salary", employee.getSalary());
            request.put("age", employee.getAge());
            request.put("title", employee.getTitle());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(ApiConstants.BASE_URL, entity, Map.class);
            Map<String, Object> data = (Map<String, Object>) response.getBody().get(ApiConstants.DATA);
            return mapToEmployee(data);
        } catch (Exception e) {
            logger.error("Error creating employee", e);
            throw new EmployeeException("Failed to create employee", e);
        }
    }

    /**
     * Deletes an employee by their unique identifier in the Mock Employee API.
     *
     * @param id the unique identifier of the employee to delete
     * @return true if the employee was deleted successfully, false otherwise
     * @throws EmployeeException if deletion fails
     */
    @Override
    public boolean deleteById(String id) throws EmployeeException {
        logger.info("Deleting employee by id: {}", id);
        try {
            Optional<Employee> employeeOpt = getById(id);
            if (employeeOpt.isEmpty()) {
                logger.warn("Employee not found for id: {}", id);
                return false;
            }
            String name = employeeOpt.get().getName();
            DeleteMockEmployeeInput input = new DeleteMockEmployeeInput();
            input.setName(name);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DeleteMockEmployeeInput> entity = new HttpEntity<>(input, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    ApiConstants.BASE_URL, HttpMethod.DELETE, entity, Map.class);
            Object data = response.getBody().get(ApiConstants.DATA);
            return Boolean.TRUE.equals(data);
        } catch (HttpClientErrorException e) {
            logger.warn("Employee not found for delete: {}", id);
            return false;
        } catch (Exception e) {
            logger.error("Error deleting employee", e);
            throw new EmployeeException("Error deleting employee", e);
        }
    }

    /**
     * Maps a response from the Mock Employee API to an {@link Employee} object.
     *
     * @param map the map containing employee attributes
     * @return the mapped {@link Employee} object, or null if the map is null
     */
    private Employee mapToEmployee(Map<String, Object> map) {
        if (map == null) return null;
        return new Employee(
                (String) map.get("id"),
                (String) map.get("employee_name"),
                ((Number) map.get("employee_salary")).intValue(),
                ((Number) map.get("employee_age")).intValue(),
                (String) map.get("employee_title"),
                (String) map.get("employee_email"));
    }
}
