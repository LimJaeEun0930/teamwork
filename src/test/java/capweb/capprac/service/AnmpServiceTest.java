package capweb.capprac.service;

import capweb.capprac.entity.Anmp;
import capweb.capprac.entity.Announcement;
import capweb.capprac.entity.Company;
import capweb.capprac.entity.User;
import capweb.capprac.repository.AnmpRepository;
import capweb.capprac.repository.AnnouncementRepository;
import capweb.capprac.repository.CompanyRepository;
import capweb.capprac.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AnmpServiceTest {

    @Autowired
    private AnmpService anmpService;
    @Autowired
    private AnmpRepository anmpRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    private Company company;
    private Announcement announcement;
    private User user;
    private Anmp anmp;
    private Date date;

    @BeforeEach
    void setUp() {
        date = new Date();
        company = new Company();
        company.setCpId("cpid");
        company.setCpPw("cppw");
        company.setCpAddr("cpaddr");
        company.setCpName("cpname");
        company.setCpCategory("cpcategory");
        company.setCpJoindate(date);
        company.setCpJoinIP("127.0.0.1");
        company.setCpMtid("cpmtid");
        company.setCpMtname("cpmtname");
        companyRepository.save(company);
        announcement = new Announcement();
        announcement.setAnmName("anmname");
        announcement.setAnmCpid(company);
        announcement.setAnmStartDate(date);
        announcement.setAnmEndDate(date);
        announcement.setAnmEmptype("anmemptype");
        announcement.setAnmRecruitm(1);
        announcementRepository.save(announcement);
        user = new User();
        user.setUsId("usid");
        user.setUsPw("uspw");
        user.setUsName("usname");
        user.setUsJoindate(date);
        user.setUsJoinIP("127.0.0.1");
        userRepository.save(user);
        anmp = new Anmp();
        anmp.setAnmpUsid(user);
        anmp.setAnmpAnmid(announcement);
        anmpRepository.save(anmp);
    }

    @Test
    public void testCreateAnmp() {
        Date update = new Date();
        User upuser;
        upuser = new User();
        upuser.setUsId("upusid");
        upuser.setUsPw("upuspw");
        upuser.setUsName("upusname");
        upuser.setUsJoindate(update);
        upuser.setUsJoinIP("123.0.0.1");
        userRepository.save(upuser);
//        Anmp createdAnmp = anmpService.createAnmp(user, announcement);
//
//        assertNotNull(createdAnmp);
//        assertEquals(user, createdAnmp.getAnmpUsid());
//        assertEquals(announcement, createdAnmp.getAnmpAnmid());
        Anmp createdAnmp = anmpService.createAnmp(upuser, announcement);
        assertNotNull(createdAnmp);
      assertEquals(upuser, createdAnmp.getAnmpUsid());
      assertEquals(announcement, createdAnmp.getAnmpAnmid());

    }

    @Test
    public void testGetAllAnmps() {

        List<Anmp> retrievedAnmps = anmpService.getAllAnmps();

        assertFalse(retrievedAnmps.isEmpty());
        //assertEquals(anmp, retrievedAnmps);
    }

    @Test
    public void testGetAnmpByIndex() {
        Anmp retrievedAnmp = anmpService.getAnmpByIndex(1);

        assertNotNull(retrievedAnmp);
        //assertEquals(anmp, retrievedAnmp);
    }

    @Test
    public void testGetAnmpsByUser() {
        List<Anmp> retrievedAnmps = anmpService.getAnmpsByUser(user);

        assertFalse(retrievedAnmps.isEmpty());
        //assertEquals(anmp, retrievedAnmps);
    }

    @Test
    public void testGetAnmpsByAnnouncement() {
        List<Anmp> retrievedAnmps = anmpService.getAnmpsByAnnouncement(announcement);

        assertFalse(retrievedAnmps.isEmpty());
        //assertEquals(anmp, retrievedAnmps);
    }

    @Test
    public void testGetAnmpsByUserAndAnnouncement() {

        List<Anmp> retrievedAnmps = anmpService.getAnmpsByUserAndAnnouncement(user, announcement);

        assertFalse(retrievedAnmps.isEmpty());
        //assertEquals(anmp, retrievedAnmps);
    }

    @Test
    public void testDeleteAnmp() {
        anmpService.deleteAnmp(anmp.getAnmpIndex());
        Anmp delanmp = anmpRepository.findAnmpByIndex(anmp.getAnmpIndex());
        assertNull(delanmp);
    }

    @Test
    public void testGetUserAnnouncements_ValidUser() {
        // 테스트 실행
        Date startDate = date; // 적절한 날짜 설정
        Date endDate = date; // 적절한 날짜 설정
        List<Anmp> results = anmpService.getUserAnnouncements("usid", startDate, endDate);

        // 검증
        assertFalse(results.isEmpty());
        //assert(results.contains(anmp)); // 결과가 anmp 객체를 포함하는지 확인
    }
    @Test
    public void testGetUserAnnouncements_InValidUser() {
        // 테스트 실행
        Date startDate = date; // 적절한 날짜 설정
        Date endDate = date; // 적절한 날짜 설정
        List<Anmp> results = anmpService.getUserAnnouncements("nonuserid", startDate, endDate);

        // 검증
        assertTrue(results.isEmpty());
        //assert(results.contains(anmp)); // 결과가 anmp 객체를 포함하는지 확인
    }


    // 여기에 추가적인 서비스 메소드들에 대한 테스트를 구현할 수 있습니다.
}
