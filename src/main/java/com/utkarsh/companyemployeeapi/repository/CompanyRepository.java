package com.utkarsh.companyemployeeapi.repository;

import com.utkarsh.companyemployeeapi.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long>{
}
