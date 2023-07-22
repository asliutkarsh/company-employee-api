package com.utkarsh.companyemployeeapi.service;

import com.utkarsh.companyemployeeapi.entities.Employee;

import java.util.List;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    Employee getEmployeeById(Long id);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee updatedEmployee, Long id);

    void deleteEmployeeById(Long id);

}
