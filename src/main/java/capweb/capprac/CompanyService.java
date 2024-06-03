package capweb.capprac;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public Company registerCompany(String cpId, String cpPw, String cpName, String cpAddr,
                                   String cpCategory, String cpMtid, String cpMtname,
                                   Date cpJoindate, String cpJoinIP) {
        // 필수값 체크
        if (cpId == null || cpId.trim().isEmpty()) {
            throw new IllegalArgumentException("Company ID cannot be null or empty.");
        }
        if (cpPw == null || cpPw.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        if (cpName == null || cpName.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be null or empty.");
        }
        if (cpAddr == null || cpAddr.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
        if (cpCategory == null || cpCategory.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty.");
        }
        if (cpMtid == null || cpMtid.trim().isEmpty()) {
            throw new IllegalArgumentException("Mentor ID cannot be null or empty.");
        }
        if (cpMtname == null || cpMtname.trim().isEmpty()) {
            throw new IllegalArgumentException("Mentor name cannot be null or empty.");
        }
        if (cpJoindate == null) {
            throw new IllegalArgumentException("Join date cannot be null.");
        }
        if (cpJoinIP == null || cpJoinIP.trim().isEmpty()) {
            throw new IllegalArgumentException("Join IP cannot be null or empty.");
        }
    // 중복 체크
        if (!companyRepository.findCompanyById(cpId).isEmpty() ||
                !companyRepository.findCompanyByName(cpName).isEmpty()||
                !companyRepository.findCompanyByAddr(cpAddr).isEmpty()||
                !companyRepository.findCompanyByMtid(cpMtid).isEmpty()) {
            throw new IllegalArgumentException("Company already exists.");
        }
        // Company 객체 생성
        Company company = new Company();
        company.setCpId(cpId);
        company.setCpPw(cpPw);
        company.setCpName(cpName);
        company.setCpAddr(cpAddr);
        company.setCpCategory(cpCategory);
        company.setCpMtid(cpMtid);
        company.setCpMtname(cpMtname);
        company.setCpJoindate(cpJoindate);
        company.setCpJoinIP(cpJoinIP);

        // Company 저장
        companyRepository.save(company);
        return company; // 회원가입 성공
    }

    // 로그인
    public Company loginCompany(String cpId, String cpPw) {
        try{
            List<Company> company = companyRepository.findCompanyById(cpId);
            if(company.get(0).getCpPw().equals(cpPw)){
                return company.get(0);
            }
        }catch(NoResultException e){

        }
        return null;
    }

    // 로그아웃 - 상태 관리가 필요한 경우 구현

    // 삭제 - 멘토 계정이 아닌 경우에만 삭제 가능
    @Transactional
    public boolean deleteCompany(int cpIndex) {
        Company company = companyRepository.findCompanyByIndex(cpIndex);
        if (company != null) {
            companyRepository.deleteByIndex(cpIndex);
            return true; // 삭제 성공
        }
        return false; // 멘토 계정이거나 회사가 없으면 삭제 실패
    }

    // 수정 - cpId, cpMtid, cpJoindate, cpJoinIP 빼고 수정 가능
    @Transactional
    public boolean updateCompany(int cpIndex, String cpPw, String cpName, String cpAddr, String cpCategory, String cpMtname, String cpFixIP) {
        Company existingCompany = companyRepository.findCompanyByIndex(cpIndex);
        int chkmodify = 0;
        if (existingCompany != null) {
            if (cpPw != null && !cpPw.trim().isEmpty()) {
                existingCompany.setCpPw(cpPw);
                chkmodify = 1;
            }
            if (cpName != null && !cpName.trim().isEmpty()) {
                List<Company> chkname = companyRepository.findCompanyByName(cpName);
                if (chkname.isEmpty()) {
                    existingCompany.setCpName(cpName);
                    chkmodify = 1;
                }
            }
            if (cpAddr != null && !cpAddr.trim().isEmpty()) {
                List<Company> chkaddr = companyRepository.findCompanyByAddr(cpAddr);
                if (chkaddr.isEmpty()) {
                    existingCompany.setCpAddr(cpAddr);
                    chkmodify = 1;
                }
            }
            if (cpCategory != null && !cpCategory.trim().isEmpty()) {
                existingCompany.setCpCategory(cpCategory);
                chkmodify = 1;
            }
            if (cpMtname != null && !cpMtname.trim().isEmpty()) {
                existingCompany.setCpMtname(cpMtname);
                chkmodify = 1;
            }
            if (cpFixIP != null && !cpFixIP.trim().isEmpty()) existingCompany.setCpFixIP(cpFixIP);
            if (chkmodify == 1) {
                existingCompany.setCpFixdate(new Date());
            }
            else {
                return false;
            }
            companyRepository.update(existingCompany);
            return true;
        }
        return false;
    }

    // 주소에 특정 문자열을 포함하는 Company 찾기
    public List<Company> findCompaniesByAddressContaining(String address){
        return companyRepository.findCompaniesByAddressContaining(address);
    }
    // 카테고리로 Company 찾기
    public List<Company> findCompaniesByCategory(String cpCategory) {
        return companyRepository.findCompaniesByCategory(cpCategory);
    }
    // 모든 Company 찾기
    public List<Company> findAllCompanies() {
        return companyRepository.findAllCompanies();
    }
    // 회사들의 카테고리 리스트 반환
    public List<String> findAllCompanyCategories() {
        return companyRepository.findAllCompanies().stream()
                .map(Company::getCpCategory)
                .distinct()
                .collect(Collectors.toList());
    }
}



