package com.utkarsh.companyemployeeapi.service.impl;

import com.utkarsh.companyemployeeapi.entities.Company;
import com.utkarsh.companyemployeeapi.repository.CompanyRepository;
import com.utkarsh.companyemployeeapi.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company updateCompany(Company updatedCompany, Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));

        if (!Objects.equals(company.getCompanyName(), updatedCompany.getCompanyName()) && company.getCompanyName() != null) {
            company.setCompanyName(updatedCompany.getCompanyName());
        }

        return companyRepository.save(company);
    }

    @Override
    public void deleteCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
        companyRepository.delete(company);
    }
}
