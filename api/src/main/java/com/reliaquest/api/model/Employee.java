package com.reliaquest.api.model;

/**
 * Represents an employee entity with attributes such as id, name, salary, age, title, and email.
 * Used for API operations involving employee data.
 *
 * @author skurade
 */
public class Employee {
    private String id;
    private String name;
    private int salary;
    private int age;
    private String title;
    private String email;

    /**
     * Constructs an {@link Employee} with the specified attributes.
     *
     * @param id the unique identifier of the employee
     * @param name the name of the employee
     * @param salary the salary of the employee
     * @param age the age of the employee
     * @param title the job title of the employee
     * @param email the email address of the employee
     */
    public Employee(String id, String name, int salary, int age, String title, String email) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.title = title;
        this.email = email;
    }

    /**
     * Default constructor for Employee.
     */
    public Employee() { }

    /**
     * Gets the unique identifier of the employee.
     *
     * @return the employee id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the employee.
     *
     * @param id the employee id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the employee.
     *
     * @return the employee name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     *
     * @param name the employee name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the salary of the employee.
     *
     * @return the employee salary
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Sets the salary of the employee.
     *
     * @param salary the employee salary
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * Gets the age of the employee.
     *
     * @return the employee age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the employee.
     *
     * @param age the employee age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the job title of the employee.
     *
     * @return the employee title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the job title of the employee.
     *
     * @param title the employee title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the email address of the employee.
     *
     * @return the employee email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the employee.
     *
     * @param email the employee email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
