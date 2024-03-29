package com.utkarsh.companyemployeeapi.service.impl;

import com.utkarsh.companyemployeeapi.entities.Company;
import com.utkarsh.companyemployeeapi.entities.Employee;
import com.utkarsh.companyemployeeapi.exception.DuplicateEntryException;
import com.utkarsh.companyemployeeapi.exception.ResourceNotFoundException;
import com.utkarsh.companyemployeeapi.repository.CompanyRepository;
import com.utkarsh.companyemployeeapi.repository.EmployeeRepository;
import com.utkarsh.companyemployeeapi.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {

        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new DuplicateEntryException("This user-email already exists");
        }

        if (employee.getCompany() != null && employee.getCompany().getId() != null) {
            Company existingCompany = companyRepository.findById(employee.getCompany().getId()).orElseThrow(() -> new ResourceNotFoundException("Company not found"));
            employee.setCompany(existingCompany);
        }
            return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee, Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!Objects.equals(employee.getFirstName(), updatedEmployee.getFirstName()) && employee.getFirstName() != null) {
            employee.setFirstName(updatedEmployee.getFirstName());
        }

        if (!Objects.equals(employee.getLastName(), updatedEmployee.getLastName()) && employee.getLastName() != null) {
            employee.setLastName(updatedEmployee.getLastName());
        }

        if (!Objects.equals(employee.getEmail(), updatedEmployee.getEmail()) && updatedEmployee.getEmail() != null) {
            if (employeeRepository.existsByEmail(updatedEmployee.getEmail())) {
                throw new DuplicateEntryException("This user-email already exists");
            }
            employee.setEmail(updatedEmployee.getEmail());
        }

        if (!Objects.equals(employee.getCompany(), updatedEmployee.getCompany()) && updatedEmployee.getCompany() != null) {
            employee.setCompany(updatedEmployee.getCompany());
        }

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeRepository.delete(employee);
    }
}
