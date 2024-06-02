package capweb.capprac;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CompanyServiceTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @BeforeEach
    public void setUp() {
        Company company = new Company();
        String ip = "192.168.1.1";
        company.setCpId("testCpUser");
        company.setCpPw("testCpPass");
        company.setCpName("회사1");
        company.setCpAddr("서울시");
        company.setCpCategory("IT");
        company.setCpMtid("MT001");
        company.setCpMtname("멘토1");
        company.setCpJoindate(new Date());
        company.setCpJoinIP(ip);
        companyRepository.save(company);
    }

    @Test
    public void testRegisterCompany() {
        Date cpJoindate = new Date();
        Company ncompany = companyService.registerCompany("testCP001", "testpassword", "test회사1", "test서울시", "testIT", "testMT001", "test멘토1",cpJoindate,"127.001");

        assertThat(ncompany).isNotNull();
    }

    @Test
    public void testLogin() {
       String cpId = "testCpUser";
       String cpPw = "testCpPass";

       Company loggedInCpUser = companyService.loginCompany(cpId, cpPw);

       assertNotNull(loggedInCpUser);
       assertEquals(loggedInCpUser.getCpId(), cpId);
    }

    @Test
    public void testDeleteCompany() {

        boolean result = companyService.deleteCompany(1);

        assertTrue(result);
    }

    @Test
    public void testUpdateCompany() {
        String npw ="testcppw";
        String nname = "testcpname";
        String naddr = "testcpaddr";
        String ncategory = "testcate";
        String nmtname = "testcpmtname";
        String nfixIP = "127.01";
        companyService.updateCompany(1,npw,nname,naddr,ncategory,nmtname,nfixIP);
        Company company1 = companyRepository.findCompanyByIndex(1);
        assertThat(company1.getCpPw()).isEqualTo(npw);
        assertThat(company1.getCpName()).isEqualTo(nname);
        assertThat(company1.getCpAddr()).isEqualTo(naddr);
        assertThat(company1.getCpCategory()).isEqualTo(ncategory);
        assertThat(company1.getCpMtname()).isEqualTo(nmtname);
        assertThat(company1.getCpFixIP()).isEqualTo(nfixIP);
    }

    @Test
    public void testFindCompaniesByAddressContaining() {

        // 테스트 실행
        List<Company> foundCompanies = companyService.findCompaniesByAddressContaining("서울");

        // 검증
        assertFalse(foundCompanies.isEmpty());
        //assertTrue(foundCompanies.contains(company));
        //주소에 "서울"이 포함된 회사가 리스트에 존재하는지 확인
        assertTrue(foundCompanies.stream().anyMatch(c -> c.getCpAddr().contains("서울")));
    }

    @Test
    public void testFindCompaniesByCategory() {

        // 테스트 실행
        List<Company> foundCompanies = companyService.findCompaniesByCategory("IT");

        // 검증
        assertFalse(foundCompanies.isEmpty());
       // assertTrue(foundCompanies.contains(company1));
        assertTrue(foundCompanies.stream().anyMatch(c -> c.getCpCategory().equals("IT")));

    }

    @Test
    public void testFindAllCompanies() {
        // 테스트 데이터 준비


        // 테스트 실행
        List<Company> allCompanies = companyService.findAllCompanies();

        // 검증
        assertFalse(allCompanies.isEmpty());
        //assertTrue(allCompanies.contains(company));
    }

    @Test
    public void testFindAllCompanyCategories() {
        // 테스트 데이터 준비

        // 테스트 실행
        List<String> categories = companyService.findAllCompanyCategories();

        // 검증
        assertFalse(categories.isEmpty());
        assertTrue(categories.contains("IT"));
    }
    // 여기에 추가적인 서비스 메소드들에 대한 테스트를 구현할 수 있습니다.
}
