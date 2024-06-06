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

    @Transactional
    //만들기 회사의 모든 사용자아이디,사용자비밀번호,이름,주소,카테고리,멘토아이디,멘토이름,가입날짜,가입아이피등을 필수 입력
    //중복체크해서 있으면 못 만들게 하기
    public Company registerCompany(String cpId, String cpPw, String cpName, String cpAddr,
                                   String cpCategory, String cpMtid, String cpMtname) {
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
        // Company 저장
        companyRepository.save(company);
        return company; // 회원가입 성공
    }

    // 회사아이디와 비밀번호 입력받아 로그인
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
    //인덱스번호,사용자비번,이름,주소,카테고리,멘토이름,수정일자등 인덱스번호를 통해 수정할수 있는것들을 입력받아서 유니크 필드는 중복검사를 하고 수정해주기
    @Transactional
    public boolean updateCompany(int cpIndex, String cpPw, String cpName, String cpAddr, String cpCategory, String cpMtname) {
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
            if (chkmodify == 1) {
                companyRepository.update(existingCompany);
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    // Read - 주소에 특정 문자열을 포함하는 Company 찾기
    public List<Company> findCompaniesByAddressContaining(String address){
        return companyRepository.findCompaniesByAddressContaining(address);
    }
    // Read - 카테고리로 Company 찾기
    public List<Company> findCompaniesByCategory(String cpCategory) {
        return companyRepository.findCompaniesByCategory(cpCategory);
    }
    // Read - 모든 Company 찾기
    public List<Company> findAllCompanies() {
        return companyRepository.findAllCompanies();
    }
    // Read - 회사들의 카테고리 리스트 반환
    public List<String> findAllCompanyCategories() {
        return companyRepository.findAllCompanies().stream()
                .map(Company::getCpCategory)
                .distinct()
                .collect(Collectors.toList());
    }
}



