package CapstoneDesign.Backendserver.service;

import CapstoneDesign.Backendserver.domain.Company;
import CapstoneDesign.Backendserver.repository.CompanyRepository;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public Company registerCompany(Company company) {
        this.validateCompany(company);
        this.checkForDuplicates(company);
        this.companyRepository.save(company);
        return company;
    }
//
//    public Company loginCompany(String cpId, String cpPw) {
//        Company companies = this.companyRepository.findCompanyById(cpId);
//        return !companies.isEmpty() && ((Company)companies.get(0)).getCpPw().equals(cpPw) ? (Company)companies.get(0) : null;
//    }

    @Transactional
    public boolean deleteCompany(String cpId) {
        Company company = this.companyRepository.findCompanyById(cpId).get();
        if (company != null) {
            this.companyRepository.deleteById(cpId);
            return true;
        } else {
            return false;
        }
    }
    // if(userRepository.findById(id).isPresent()){
//        return userRepository.findById(id).get();
    @Transactional
    public boolean updateCompany(Company updatedCompany) {
        Company existingCompany;
        if(companyRepository.findCompanyById(updatedCompany.getCpId()).isPresent()){
            existingCompany = companyRepository.findCompanyById(updatedCompany.getCpId()).get();
        } else {
            existingCompany = null;
        }
        if (existingCompany != null) {
            this.updateCompanyDetails(existingCompany, updatedCompany);
            this.companyRepository.update(existingCompany);
            return true;
        } else {
            return false;
        }
    }

    public List<Company> findCompaniesByAddressContaining(String address) {
        return this.companyRepository.findCompaniesByAddressContaining(address);
    }

    public List<Company> findCompaniesByCategory(String cpCategory) {
        return this.companyRepository.findCompaniesByCategory(cpCategory);
    }

    public List<Company> findAllCompanies() {
        return this.companyRepository.findAllCompanies();
    }

    public List<String> findAllCompanyCategories() {
        return (List)this.companyRepository.findAllCompanies().stream().map(Company::getCpCategory).distinct().collect(Collectors.toList());
    }

    private void validateCompany(Company company) {
        if (company.getCpId() != null && !company.getCpId().trim().isEmpty()) {
            if (company.getCpPw() != null && !company.getCpPw().trim().isEmpty()) {
                if (company.getCpName() != null && !company.getCpName().trim().isEmpty()) {
                    if (company.getCpAddr() != null && !company.getCpAddr().trim().isEmpty()) {
                        if (company.getCpCategory() != null && !company.getCpCategory().trim().isEmpty()) {

                        } else {
                            throw new IllegalArgumentException("Category cannot be null or empty.");
                        }
                    } else {
                        throw new IllegalArgumentException("Address cannot be null or empty.");
                    }
                } else {
                    throw new IllegalArgumentException("Company name cannot be null or empty.");
                }
            } else {
                throw new IllegalArgumentException("Password cannot be null or empty.");
            }
        } else {
            throw new IllegalArgumentException("Company ID cannot be null or empty.");
        }
    }

    private void checkForDuplicates(Company company) {
        if (!this.companyRepository.findCompanyById(company.getCpId()).isEmpty()) {
            throw new IllegalArgumentException("A company with the same ID already exists.");
        } else if (!this.companyRepository.findCompanyByName(company.getCpName()).isEmpty()) {
            throw new IllegalArgumentException("A company with the same name already exists.");
        }
    }

    private void updateCompanyDetails(Company existingCompany, Company updatedCompany) {
        if (updatedCompany.getCpPw() != null && !updatedCompany.getCpPw().trim().isEmpty()) {
            existingCompany.setCpPw(updatedCompany.getCpPw());
        }

        if (updatedCompany.getCpName() != null && !updatedCompany.getCpName().trim().isEmpty()) {
            existingCompany.setCpName(updatedCompany.getCpName());
        }

        if (updatedCompany.getCpAddr() != null && !updatedCompany.getCpAddr().trim().isEmpty()) {
            existingCompany.setCpAddr(updatedCompany.getCpAddr());
        }

        if (updatedCompany.getCpCategory() != null && !updatedCompany.getCpCategory().trim().isEmpty()) {
            existingCompany.setCpCategory(updatedCompany.getCpCategory());
        }


    }
    @Transactional(readOnly = true)
    public boolean validateDuplicateCompany(String cpid) {
        log.info("중복체크함수실행 id={}",cpid);
        return companyRepository.findCompanyById(cpid).isPresent();

    }


    public Object findCompany(String cpid){
        if(companyRepository.findCompanyById(cpid).isPresent()){
            return companyRepository.findCompanyById(cpid).get();
        } else return "ID_NOT_FOUND";
    }
}