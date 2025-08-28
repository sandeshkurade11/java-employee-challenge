package com.reliaquest.api.service;

import com.reliaquest.api.model.Employee;
import java.util.List;

/**
 * Service interface for employee-related business logic and operations.
 * Defines methods for retrieving, searching, creating, and deleting employees,
 * as well as salary-based queries.
 *
 * @author skurade
 */
public interface IEmployeeService {

    /**
     * Retrieves all employees.
     *
     * @return a list of all {@link Employee} objects
     */
    List<Employee> getAllEmployees();

    /**
     * Searches for employees whose names contain the specified fragment.
     *
     * @param nameFragment the substring to search for in employee names
     * @return a list of matching {@link Employee} objects
     */
    List<Employee> searchEmployeesByName(String nameFragment);

    /**
     * Retrieves an employee by their unique identifier.
     *
     * @param id the unique identifier of the employee
     * @return the {@link Employee} object if found, otherwise null
     */
    Employee getEmployeeById(String id);

    /**
     * Gets the highest salary among all employees.
     *
     * @return the highest salary as an integer
     */
    int getHighestSalary();

    /**
     * Retrieves the names of the top 10 highest earning employees.
     *
     * @return a list of employee names
     */
    List<String> getTop10HighestEarningEmployeeNames();

    /**
     * Creates a new employee.
     *
     * @param employee the {@link Employee} object to create
     * @return the created {@link Employee} object
     */
    Employee createEmployee(Object employee);

    /**
     * Deletes an employee by their unique identifier.
     *
     * @param id the unique identifier of the employee to delete
     * @return the name of the deleted employee, or null if not found
     */
    String deleteEmployeeById(String id);
}
