package capweb.capprac.repository;

import capweb.capprac.entity.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {
        Company company = new Company();
        company.setCpId("cpid1");
        company.setCpPw("cppw1");
        company.setCpAddr("cpaddr1");
        company.setCpCategory("cpcategory");
        company.setCpName("cpname1");
        company.setCpMtid("cpmtid1");
        company.setCpMtname("cpmtname1");
        companyRepository.save(company);
    }

    @Test
    public void testSave() {
        Company company1 = new Company(); // 생성자를 가정
        company1.setCpId("testcpid");
        company1.setCpPw("testcppw");
        company1.setCpAddr("testcpaddr");
        company1.setCpCategory("testcpcate");
        company1.setCpName("testcpname");
        company1.setCpMtid("testcpmtid");
        company1.setCpMtname("testcpmtname");
        // company 속성 설정
        companyRepository.save(company1);
        assertNotNull(company1); // getId()가 생성된 ID를 검색하는 방법이라고 가정
    }

    @Test
    public void testFindCompanyByIndex() {
        Company found = companyRepository.findCompanyByIndex(1);
        assertNotNull(found);
    }

    @Test
    public void testFindAllCompanies() {
        List<Company> companies = companyRepository.findAllCompanies();
        assertFalse(companies.isEmpty());
    }

    @Test
    public void testUpdate() {
        Company company = companyRepository.findCompanyByIndex(1);
        company.setCpName("새로운 회사 이름"); // setCpName() 메소드가 있다고 가정
        companyRepository.update(company);
        Company updatedCompany = companyRepository.findCompanyByIndex(1);
        assertEquals("새로운 회사 이름", updatedCompany.getCpName());
    }

    @Test
    public void testDeleteByIndex() {
        Company company1 = new Company(); // 생성자를 가정
        company1.setCpId("testcpid");
        company1.setCpPw("testcppw");
        company1.setCpAddr("testcpaddr");
        company1.setCpCategory("testcpcate");
        company1.setCpName("testcpname");
        company1.setCpMtid("testcpmtid");
        company1.setCpMtname("testcpmtname");
        companyRepository.save(company1);
        int idx = company1.getCpIndex(); // ID 가져오기
        companyRepository.deleteByIndex(idx);
        assertNull(companyRepository.findCompanyByIndex(idx));
    }

    // findCompanyById, findCompanyByName, findCompanyByAddr, findCompaniesByCategory, findCompanyByMtid, findCompaniesByMtname, findCompaniesByFixdate, findCompaniesByFixIP, findCompaniesByJoindate, findCompaniesByJoinIP, findCompaniesByAddressContaining 메소드에 대한 테스트 케이스도 유사한 방식으로 구현할 수 있습니다.
    // 각 메소드에 대한 테스트 케이스는 해당 메소드가 예상대로 작동하는지 확인하기 위해 필요한 검증 로직을 포함해야 합니다.
// ... 이전 코드 ...

    @Test
    public void testFindCompanyById() {
        Company company1 = new Company();
        company1.setCpPw("testcppw");
        company1.setCpAddr("testcpaddr");
        company1.setCpCategory("testcpcate");
        company1.setCpName("testcpname");
        company1.setCpId("CP001");
        company1.setCpMtid("testcpmtid");
        company1.setCpMtname("testcpmtname");
        companyRepository.save(company1);
        List<Company> found = companyRepository.findCompanyById("CP001");
        assertNotNull(found);
        assertEquals("CP001", found.get(0).getCpId());
    }

    @Test
    public void testFindCompanyByName() {
        Company company1 = new Company();
        company1.setCpPw("testcppw");
        company1.setCpAddr("testcpaddr");
        company1.setCpCategory("testcpcate");
        company1.setCpName("testcpname");
        company1.setCpId("CP001");
        company1.setCpMtid("testcpmtid");
        company1.setCpMtname("testcpmtname");
        companyRepository.save(company1);
        List<Company> found = companyRepository.findCompanyByName("testcpname");
        assertNotNull(found);
        assertEquals("testcpname", found.get(0).getCpName());
    }

    @Test
    public void testFindCompanyByAddr() {
        Company company1 = new Company();
        company1.setCpPw("testcppw");
        company1.setCpAddr("testcpaddr");
        company1.setCpCategory("testcpcate");
        company1.setCpName("testcpname");
        company1.setCpId("CP001");
        company1.setCpMtid("testcpmtid");
        company1.setCpMtname("testcpmtname");
        companyRepository.save(company1);
        List<Company> found = companyRepository.findCompanyByAddr("testcpaddr");
        assertNotNull(found);
        assertEquals("testcpaddr", found.get(0).getCpAddr());
    }

    @Test
    public void testFindCompaniesByCategory() {
        Company company1 = new Company();
        company1.setCpPw("testcppw");
        company1.setCpAddr("testcpaddr");
        company1.setCpCategory("testcpcategory");
        company1.setCpName("testcpname");
        company1.setCpId("CP001");
        company1.setCpMtid("testcpmtid");
        company1.setCpMtname("testcpmtname");
        companyRepository.save(company1);
        List<Company> found = companyRepository.findCompaniesByCategory("testcpcategory");
        assertFalse(found.isEmpty());
        assertEquals("testcpcategory", found.get(0).getCpCategory());
    }

    @Test
    public void testFindCompanyByMtid() {
        Company company1 = new Company();
        company1.setCpPw("testcppw");
        company1.setCpAddr("testcpaddr");
        company1.setCpCategory("testcpcate");
        company1.setCpName("testcpname");
        company1.setCpId("CP001");
        company1.setCpMtid("testcpmtid");
        company1.setCpMtname("testcpmtname");
        companyRepository.save(company1);
        List<Company> found = companyRepository.findCompanyByMtid("testcpmtid");
        assertNotNull(found);
        assertEquals("testcpmtid", found.get(0).getCpMtid());
    }

    @Test
    public void testFindCompaniesByMtname() {
        Company company1 = new Company();
        company1.setCpPw("testcppw");
        company1.setCpAddr("testcpaddr");
        company1.setCpCategory("testcpcate");
        company1.setCpName("testcpname");
        company1.setCpId("CP001");
        company1.setCpMtid("testcpmtid");
        company1.setCpMtname("회의1");
        companyRepository.save(company1);
        List<Company> found = companyRepository.findCompaniesByMtname("회의1");
        assertFalse(found.isEmpty());
        assertEquals("회의1", found.get(0).getCpMtname());
    }





// ... 추가적인 메소드들에 대한 테스트를 여기에 구현할 수 있습니다 ...

    // 추가적인 메소드들에 대한 테스트를 여기에 구현할 수 있습니다.
}
