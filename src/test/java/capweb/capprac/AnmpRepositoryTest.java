package capweb.capprac;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AnmpRepositoryTest {

    @Autowired
    private EntityManager entityManager;

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
    public void testSave() {

        assertNotNull(entityManager.find(Anmp.class, anmp.getAnmpIndex())); // getAnmpIndex()가 생성된 ID를 검색하는 방법이라고 가정
    }

    @Test
    public void testFindAnmpByIndex() {
        int id = anmp.getAnmpIndex();
        Anmp found = anmpRepository.findAnmpByIndex(id);
        assertNotNull(found);
    }

    @Test
    public void testFindAllAnmps() {
        List<Anmp> anmps = anmpRepository.findAllAnmps();
        assertFalse(anmps.isEmpty());
    }

    @Test
    public void testUpdate() {
        String beforeanm = anmp.getAnmpAnmid().getAnmName();
        Announcement updateannouncement;
        updateannouncement = new Announcement();
        updateannouncement.setAnmName("updateanmname");
        updateannouncement.setAnmCpid(company);
        updateannouncement.setAnmStartDate(date);
        updateannouncement.setAnmEndDate(date);
        updateannouncement.setAnmEmptype("anmemptype");
        updateannouncement.setAnmRecruitm(1);
        announcementRepository.save(updateannouncement);
        anmp.setAnmpAnmid(updateannouncement);
        anmpRepository.update(anmp);
        Anmp updatedAnmp = anmpRepository.findAnmpByIndex(anmp.getAnmpIndex());
        assertNotEquals(beforeanm,anmp.getAnmpAnmid().getAnmName());
    }

    @Test
    public void testDeleteByIndex() {
        int id = anmp.getAnmpIndex(); // ID 가져오기
        anmpRepository.deleteByIndex(id);
        assertNull(entityManager.find(Anmp.class, id));
    }

    @Test
    public void testFindAnmpsByUser() {
        List<Anmp> found = anmpRepository.findAnmpsByUser(user);
        assertFalse(found.isEmpty());
        //assertEquals(user, found.get(0).getAnmpUsid());
    }

    @Test
    public void testFindAnmpsByAnnouncement() {
        List<Anmp> found = anmpRepository.findAnmpsByAnnouncement(announcement);
        assertFalse(found.isEmpty());
        //assertEquals(announcement, found.get(0).getAnmpAnmid());
    }

    @Test
    public void testFindAnmpsByUserAndAnnouncement() {
        List<Anmp> found = anmpRepository.findAnmpsByUserAndAnnouncement(user, announcement);
        assertFalse(found.isEmpty());
        //assertTrue(found.stream().allMatch(a -> a.getAnmpUsid().equals(user) && a.getAnmpAnmid().equals(announcement)));
    }

    // 여기에 추가적인 메소드들에 대한 테스트를 구현할 수 있습니다.
}
