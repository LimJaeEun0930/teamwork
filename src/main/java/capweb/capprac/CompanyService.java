package capweb.capprac;

import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public boolean registerCompany(String cpId, String cpPw, String cpName, String cpAddr,
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
        if (companyRepository.findCompanyById(cpId) != null ||
                companyRepository.findCompanyByName(cpName) != null ||
                companyRepository.findCompanyByAddr(cpAddr) != null ||
                companyRepository.findCompanyByMtid(cpMtid) != null) {
            return false; // 중복이 있으면 회원가입 실패
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
        return true; // 회원가입 성공
    }

    // 로그인
    public Optional<Company> login(String cpId, String cpPw) {
        return Optional.ofNullable(companyRepository.findCompanyById(cpId))
                .filter(company -> company.getCpPw().equals(cpPw));
    }

    // 로그아웃 - 상태 관리가 필요한 경우 구현

    // 삭제 - 멘토 계정이 아닌 경우에만 삭제 가능
    public boolean deleteCompany(int cpIndex) {
        Company company = companyRepository.findCompanyByIndex(cpIndex);
        if (company != null) {
            companyRepository.deleteByIndex(cpIndex);
            return true; // 삭제 성공
        }
        return false; // 멘토 계정이거나 회사가 없으면 삭제 실패
    }

    // 수정 - cpId, cpMtid, cpJoindate, cpJoinIP 빼고 수정 가능
    public boolean updateCompany(Company updatedCompany) {
        Company existingCompany = companyRepository.findCompanyByIndex(updatedCompany.getCpIndex());
        if (existingCompany == null) {
            return false; // 회사가 없으면 수정 실패
        }
        // 유니크 필드 체크
        if (!existingCompany.getCpName().equals(updatedCompany.getCpName()) &&
                companyRepository.findCompanyByName(updatedCompany.getCpName()) != null) {
            return false; // 이름 중복 체크
        }
        if (!existingCompany.getCpAddr().equals(updatedCompany.getCpAddr()) &&
                companyRepository.findCompanyByAddr(updatedCompany.getCpAddr()) != null) {
            return false; // 주소 중복 체크
        }
        // 수정 가능한 필드 업데이트
        existingCompany.setCpPw(updatedCompany.getCpPw());
        existingCompany.setCpName(updatedCompany.getCpName());
        existingCompany.setCpAddr(updatedCompany.getCpAddr());
        existingCompany.setCpCategory(updatedCompany.getCpCategory());
        existingCompany.setCpMtname(updatedCompany.getCpMtname());
        existingCompany.setCpFixdate(updatedCompany.getCpFixdate());
        existingCompany.setCpFixIP(updatedCompany.getCpFixIP());
        companyRepository.update(existingCompany);
        return true; // 수정 성공
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



