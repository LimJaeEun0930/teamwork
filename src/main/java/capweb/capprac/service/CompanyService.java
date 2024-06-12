package capweb.capprac.service;

import capweb.capprac.entity.Company;
import capweb.capprac.repository.CompanyRepository;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    //만들기 회사의 모든 사용자아이디,사용자비밀번호,이름,주소,카테고리,멘토아이디,멘토이름,가입날짜,가입아이피등을 필수 입력
    //중복체크해서 있으면 못 만들게 하기
    @Transactional
    public Company registerCompany(Company company) {
        validateCompany(company);
        checkForDuplicates(company);
        companyRepository.save(company);
        return company; // 회원가입 성공
    }

    public Company loginCompany(String cpId, String cpPw) {
        List<Company> companies = companyRepository.findCompanyById(cpId);
        if (!companies.isEmpty() && companies.get(0).getCpPw().equals(cpPw)) {
            return companies.get(0); // 로그인 성공
        }
        return null; // 로그인 실패
    }

    // 로그아웃 메소드는 상태 관리가 필요하므로, 세션 또는 토큰 기반 인증 시스템에서 구현해야 합니다.

    @Transactional
    public boolean deleteCompany(int cpIndex) {
        Company company = companyRepository.findCompanyByIndex(cpIndex);
        if (company != null) {
            companyRepository.deleteByIndex(cpIndex);
            return true; // 삭제 성공
        }
        return false; // 멘토 계정이거나 회사가 없으면 삭제 실패
    }

    @Transactional
    public boolean updateCompany(Company updatedCompany) {
        Company existingCompany = companyRepository.findCompanyByIndex(updatedCompany.getCpIndex());
        if (existingCompany != null) {
            updateCompanyDetails(existingCompany, updatedCompany);
            companyRepository.update(existingCompany);
            return true; // 수정 성공
        }
        return false; // 회사가 없으면 수정 실패
    }

    public List<Company> findCompaniesByAddressContaining(String address) {
        return companyRepository.findCompaniesByAddressContaining(address);
    }

    public List<Company> findCompaniesByCategory(String cpCategory) {
        return companyRepository.findCompaniesByCategory(cpCategory);
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAllCompanies();
    }

    public List<String> findAllCompanyCategories() {
        return companyRepository.findAllCompanies().stream()
                .map(Company::getCpCategory)
                .distinct()
                .collect(Collectors.toList());
    }
    private void validateCompany(Company company) {
        if (company.getCpId() == null || company.getCpId().trim().isEmpty()) {
            throw new IllegalArgumentException("Company ID cannot be null or empty.");
        }
        if (company.getCpPw() == null || company.getCpPw().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        if (company.getCpName() == null || company.getCpName().trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be null or empty.");
        }
        if (company.getCpAddr() == null || company.getCpAddr().trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
        if (company.getCpCategory() == null || company.getCpCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty.");
        }
        if (company.getCpMtid() == null || company.getCpMtid().trim().isEmpty()) {
            throw new IllegalArgumentException("Mentor ID cannot be null or empty.");
        }
        if (company.getCpMtname() == null || company.getCpMtname().trim().isEmpty()) {
            throw new IllegalArgumentException("Mentor name cannot be null or empty.");
        }
    }

    private void checkForDuplicates(Company company) {
        if (!companyRepository.findCompanyById(company.getCpId()).isEmpty()) {
            throw new IllegalArgumentException("A company with the same ID already exists.");
        }
        if (!companyRepository.findCompanyByName(company.getCpName()).isEmpty()) {
            throw new IllegalArgumentException("A company with the same name already exists.");
        }
        // ... 다른 중복 체크 로직 ...
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
        if (updatedCompany.getCpMtname() != null && !updatedCompany.getCpMtname().trim().isEmpty()) {
            existingCompany.setCpMtname(updatedCompany.getCpMtname());
        }
        // ... 다른 업데이트 로직 ...
    }
}



