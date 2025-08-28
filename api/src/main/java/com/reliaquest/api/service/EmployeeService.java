package com.reliaquest.api.service;

import com.reliaquest.api.exception.EmployeeException;
import com.reliaquest.api.exception.EmployeeRuntimeException;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.repository.IEmployeeRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Employee operations such as retrieval, creation, search, and deletion.
 * Handles business logic and interacts with the Employee repository.
 *
 * @author skurade
 */
@Service
public class EmployeeService implements IEmployeeService {

    private final IEmployeeRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    /**
     * Constructs an EmployeeService with the specified repository.
     *
     * @param repository the employee repository
     */
    public EmployeeService(IEmployeeRepository repository) {
        this.repository = repository;
    }

    /**
     * Converts a key-value map to an Employee object.
     *
     * @param empMap the map containing employee properties
     * @return the constructed Employee object
     */
    private Employee createEmployeeFromMap(Map<String, Object> empMap) {
        Employee employee = new Employee();
        employee.setName((String) empMap.get("name"));
        employee.setSalary((Integer) empMap.get("salary"));
        employee.setAge((Integer) empMap.get("age"));
        employee.setTitle((String) empMap.get("title"));
        return employee;
    }

    /**
     * Retrieves all employees from the repository.
     *
     * @return a list of all employees
     */
    @Override
    public List<Employee> getAllEmployees() {
        LOGGER.info("Fetching all employees");
        try {
            return repository.getAll();
        } catch (EmployeeException e) {
            throw new EmployeeRuntimeException("Failed to fetch all employees", e);
        }
    }

    /**
     * Searches employees by a name fragment.
     *
     * @param nameFragment the fragment of the employee name to search for
     * @return a list of employees matching the name fragment
     */
    @Override
    public List<Employee> searchEmployeesByName(String nameFragment) {
        LOGGER.info("Searching employees by name : {}", nameFragment);
        try {
            return repository.getAll().stream()
                    .filter(e -> e.getName().toLowerCase().contains(nameFragment.toLowerCase()))
                    .collect(Collectors.toList());
        } catch (EmployeeException e) {
            throw new EmployeeRuntimeException("Failed to search employees by name : " + nameFragment, e);
        }
    }

    /**
     * Retrieves an employee by their unique identifier.
     *
     * @param id the employee's unique identifier
     * @return the employee with the specified id
     */
    @Override
    public Employee getEmployeeById(String id) {
        LOGGER.info("Fetching employee by id: {}", id);
        try {
            return repository
                    .getById(id)
                    .orElseThrow(() -> new EmployeeRuntimeException("Employee not found for id: " + id));
        } catch (EmployeeException e) {
            throw new EmployeeRuntimeException("Failed to fetch employee by id: " + id, e);
        }
    }

    /**
     * Gets the highest salary among all employees.
     *
     * @return the highest salary value
     */
    @Override
    public int getHighestSalary() {
        LOGGER.info("Getting highest salary among employees");
        try {
            return repository.getAll().stream()
                    .mapToInt(Employee::getSalary)
                    .max()
                    .orElse(0);
        } catch (EmployeeException e) {
            throw new EmployeeRuntimeException("Failed to get highest salary among employees", e);
        }
    }

    /**
     * Retrieves the names of the top 10 highest earning employees.
     *
     * @return a list of names of the top 10 highest earning employees
     */
    @Override
    public List<String> getTop10HighestEarningEmployeeNames() {
        LOGGER.info("Getting top 10 highest earning employees");
        try {
            return repository.getAll().stream()
                    .sorted((e1, e2) -> Integer.compare(e2.getSalary(), e1.getSalary()))
                    .limit(10)
                    .map(Employee::getName)
                    .collect(Collectors.toList());
        } catch (EmployeeException e) {
            throw new EmployeeRuntimeException("Failed to get top 10 highest earning employees", e);
        }
    }

    /**
     * Creates a new employee from the provided input object.
     *
     * @param emp the input object containing employee data
     * @return the created Employee object
     */
    @Override
    public Employee createEmployee(Object emp) {
        Employee employee = createEmployeeFromMap((Map<String, Object>) emp);
        LOGGER.info("Creating employee: {}", employee.getName());
        try {
            return repository.create(employee);
        } catch (EmployeeException e) {
            throw new EmployeeRuntimeException("Failed to create employee: " + employee.getName(), e);
        }
    }

    /**
     * Deletes an employee by their unique identifier.
     *
     * @param id the employee's unique identifier
     * @return the name of the deleted employee
     */
    @Override
    public String deleteEmployeeById(String id) {
        LOGGER.info("Deleting employee by id: {}", id);
        Employee emp = getEmployeeById(id);
        try {
            if (repository.deleteById(id)) {

                return emp.getName();
            } else {
                throw new EmployeeRuntimeException("Employee not found or could not be deleted for id: " + id);
            }
        } catch (EmployeeException e) {
            throw new EmployeeRuntimeException("Failed to delete employee by id: " + id, e);
        }
    }
}
