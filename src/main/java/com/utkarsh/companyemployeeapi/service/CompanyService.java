package com.utkarsh.companyemployeeapi.service;

import com.utkarsh.companyemployeeapi.entities.Company;
import com.utkarsh.companyemployeeapi.entities.Employee;

import java.util.List;

public interface CompanyService {

    Company saveCompany(Company company);

    Company getCompanyById(Long id);

    List<Company> getAllCompanies();

    Company updateCompany(Company updatedCompany, Long id);

    void deleteCompanyById(Long id);

}
