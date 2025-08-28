package com.reliaquest.api.repository;

import com.reliaquest.api.exception.EmployeeException;
import com.reliaquest.api.model.Employee;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Employee data operations.
 * Defines methods for retrieving, creating, and deleting Employee entities.
 *
 * @author skurade
 */
public interface IEmployeeRepository {

    /**
     * Retrieves all employees from the data source.
     *
     * @return a list of all {@link Employee} objects
     */
    List<Employee> getAll() throws EmployeeException;

    /**
     * Retrieves an employee by their unique identifier.
     *
     * @param id the unique identifier of the employee
     * @return an {@link Optional} containing the {@link Employee} if found, or empty if not found
     */
    Optional<Employee> getById(String id) throws EmployeeException;

    /**
     * Creates a new employee in the data source.
     *
     * @param employee the {@link Employee} object to create
     * @return the created {@link Employee} object
     */
    Employee create(Employee employee) throws EmployeeException;

    /**
     * Deletes an employee by their unique identifier.
     *
     * @param id the unique identifier of the employee to delete
     * @return true if the employee was deleted successfully, false otherwise
     */
    boolean deleteById(String id) throws EmployeeException;
}
