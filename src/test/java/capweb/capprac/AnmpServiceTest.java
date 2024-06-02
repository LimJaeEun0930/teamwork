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
import java.util.ArrayList;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.Mockito.*;
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
    private  CompanyRepository companyRepository;

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

    // 여기에 추가적인 서비스 메소드들에 대한 테스트를 구현할 수 있습니다.
}
