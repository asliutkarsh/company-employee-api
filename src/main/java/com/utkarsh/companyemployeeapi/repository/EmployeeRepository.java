package com.utkarsh.companyemployeeapi.repository;

import com.utkarsh.companyemployeeapi.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Boolean existsByEmail(String email);
}
