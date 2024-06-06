package capweb.capprac.repository;

import capweb.capprac.entity.Announcement;
import capweb.capprac.entity.Company;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AnnouncementRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Company company;
    private Announcement announcement;
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
    }
    @Test
    public void testSave() {
        assertThat(announcement).isNotNull(); // getAnmIndex()가 생성된 ID를 검색하는 방법이라고 가정
    }

    @Test
    public void testFindAnnouncementByIndex() {
        int id = announcement.getAnmIndex();
        Announcement found = announcementRepository.findAnnouncementByIndex(id);
        assertNotNull(found);
    }

    @Test
    public void testFindAllAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAllAnnouncements();
        assertFalse(announcements.isEmpty());
    }

    @Test
    public void testUpdate() {
        announcement.setAnmName("새로운 공고 이름"); // setAnmName() 메소드가 있다고 가정
        announcementRepository.update(announcement);
        Announcement updatedAnnouncement = announcementRepository.findAnnouncementByIndex(announcement.getAnmIndex());
        assertEquals("새로운 공고 이름", updatedAnnouncement.getAnmName());
    }

    @Test
    public void testDeleteByIndex() {
        int id = announcement.getAnmIndex(); // ID 가져오기
        announcementRepository.deleteByIndex(id);
        assertNull(entityManager.find(Announcement.class, id));
    }

    @Test
    public void testFindAnnouncementsByName() {
        announcement.setAnmName("공고1");
        announcementRepository.update(announcement);
        List<Announcement> found = announcementRepository.findAnnouncementsByName("공고1");
        Assertions.assertThat(found).isNotEmpty();
        assertEquals("공고1", found.get(0).getAnmName());
    }

    @Test
    public void testFindAnnouncementsByPeriod() {
        Date startDate = date;
        Date endDate = date;
        announcement.setAnmStartDate(startDate);
        announcement.setAnmEndDate(endDate);
        announcementRepository.update(announcement);
        List<Announcement> found = announcementRepository.findAnnouncementsByPeriod(startDate, endDate);
        assertFalse(found.isEmpty());
        //assertTrue(found.get(0).getAnmStartDate().equals(startDate) && found.get(0).getAnmEndDate().equals(endDate));
    }

    @Test
    public void testFindAnnouncementsByEmptype() {
        announcement.setAnmEmptype("정규직");
        announcementRepository.update(announcement);
        List<Announcement> found = announcementRepository.findAnnouncementsByEmptype("정규직");
        assertFalse(found.isEmpty());
        assertEquals("정규직", found.get(0).getAnmEmptype());
    }

    @Test
    public void testFindAnnouncementsByRecruitm() {
        announcement.setAnmRecruitm(5);
        announcementRepository.update(announcement);
        List<Announcement> found = announcementRepository.findAnnouncementsByRecruitm(5);
        assertFalse(found.isEmpty());
        assertEquals(5, found.get(0).getAnmRecruitm());
    }

    @Test
    public void testFindAnnouncementsByCompany() {
        List<Announcement> found = announcementRepository.findAnnouncementsByCompany(company);
        assertFalse(found.isEmpty());
        //assertEquals(company, found.get(0).getAnmCpid());
    }
    @Test
    public void whenFindByDateRangeAndCompany_thenReturnTours() {
        Date stdate = date;
        Date enddate = date;
        List<Announcement>fanms = announcementRepository.findAnnouncementsByDateRangeAndCompany(stdate,enddate,company);
        assertFalse(fanms.isEmpty());
    }

    // 여기에 추가적인 메소드들에 대한 테스트를 구현할 수 있습니다.
}
