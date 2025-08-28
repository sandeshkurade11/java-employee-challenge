package com.reliaquest.api.controller.employee;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.IEmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for employee operations.
 * Implements endpoints for retrieving, searching, creating, and deleting employees.
 * Delegates business logic to the {@link IEmployeeService}.
 *
 * @author skurade
 */
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController implements IEmployeeController {

    @Autowired
    private IEmployeeService service;

    /**
     * Retrieves all employees.
     * Delegates to the service layer to fetch the list.
     *
     * @return a {@link ResponseEntity} containing a list of {@link Employee} objects
     */
    @Override
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    /**
     * Searches employees by name fragment.
     * Delegates to the service layer to find matching employees.
     *
     * @param searchString the name fragment to search for
     * @return a {@link ResponseEntity} containing a list of matching {@link Employee} objects
     */
    @Override
    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) {
        return ResponseEntity.ok(service.searchEmployeesByName(searchString));
    }

    /**
     * Retrieves an employee by their unique identifier.
     * Delegates to the service layer to fetch the employee.
     *
     * @param id the unique identifier of the employee
     * @return a {@link ResponseEntity} containing the {@link Employee} if found, or 404 if not found
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        Employee emp = service.getEmployeeById(id);
        return emp != null ? ResponseEntity.ok(emp) : ResponseEntity.notFound().build();
    }

    /**
     * Retrieves the highest salary among all employees.
     * Delegates to the service layer to compute the highest salary.
     *
     * @return a {@link ResponseEntity} containing the highest salary as an {@link Integer}
     */
    @Override
    @GetMapping("/highestSalary")
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return ResponseEntity.ok(service.getHighestSalary());
    }

    /**
     * Retrieves the top 10 highest earning employees.
     * Delegates to the service layer to fetch the list.
     *
     * @return a {@link ResponseEntity} containing a list of top earning {@link Employee} objects
     */
    @Override
    @GetMapping("/topTenHighestEarningEmployeeNames")
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return ResponseEntity.ok(service.getTop10HighestEarningEmployeeNames());
    }

    /**
     * Creates a new employee.
     * Delegates to the service layer to create and return the employee.
     *
     * @param employee the {@link Employee} object to create
     * @return a {@link ResponseEntity} containing the created {@link Employee}
     */
    @Override
    @PostMapping
    public ResponseEntity<Object> createEmployee(@RequestBody Object employee) {
        return ResponseEntity.ok(service.createEmployee(employee));
    }

    /**
     * Deletes an employee by their unique identifier.
     * Delegates to the service layer to delete the employee.
     *
     * @param id the unique identifier of the employee to delete
     * @return a {@link ResponseEntity} containing the name of the deleted employee, or 404 if not found
     */
    @Override
    @PostMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        String name = service.deleteEmployeeById(id);
        return name != null
                ? ResponseEntity.ok(name)
                : ResponseEntity.notFound().build();
    }
}
