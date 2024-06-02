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
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AnnouncementServiceTest {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AnnouncementService announcementService;

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
    }

    @Test
    public void testCreateAnnouncement() {
        Date startDate = new Date();
        Date endDate = new Date();

        Company ucompany;
        ucompany = new Company();
        ucompany.setCpId("ucpid");
        ucompany.setCpPw("cppw");
        ucompany.setCpAddr("ucpaddr");
        ucompany.setCpName("ucpname");
        ucompany.setCpCategory("cpcategory");
        ucompany.setCpJoindate(date);
        ucompany.setCpJoinIP("147.0.0.1");
        ucompany.setCpMtid("ucpmtid");
        ucompany.setCpMtname("ucpmtname");
        companyRepository.save(ucompany);

        // 저장 및 검증
        //Announcement createdAnnouncement = announcementService.createAnnouncement("New Announcement", startDate, endDate, "Full-time", 5, company);
        Announcement createdAnnouncement = announcementService.createAnnouncement("New Announcement", startDate, endDate, "Full-time", 5, ucompany);
        assertNotNull(createdAnnouncement);
        assertEquals("New Announcement", createdAnnouncement.getAnmName());
        assertEquals(startDate, createdAnnouncement.getAnmStartDate());
        assertEquals(endDate, createdAnnouncement.getAnmEndDate());
        assertEquals("Full-time", createdAnnouncement.getAnmEmptype());
        assertEquals(5, createdAnnouncement.getAnmRecruitm());
        assertEquals(ucompany, createdAnnouncement.getAnmCpid());
    }
    @Test
    public void testDeleteAnnouncement() {
        announcementService.deleteAnnouncement(1);
        Announcement found = announcementRepository.findAnnouncementByIndex(1);
      assertNull(found);
    }

    @Test
    public void testUpdateAnnouncement() {
       Date startDate = new Date();
       Date endDate = new Date();
        announcementService.updateAnnouncement(1, "Updated Announcement", startDate, endDate, "Part-time", 10,company);
        assertNotNull(announcementRepository.findAnnouncementByIndex(1));
        //assertEquals("Updated Announcement",announcementRepository.findAnnouncementByIndex(1).getAnmName());
        //assertEquals("Part-time",announcementRepository.findAnnouncementByIndex(1).getAnmEmptype());
        //assertEquals(10,announcementRepository.findAnnouncementByIndex(1).getAnmRecruitm());
    }

    @Test
    public void testGetAllAnnouncements() {
        List<Announcement> retrievedAnnouncements = announcementService.getAllAnnouncements();

        assertFalse(retrievedAnnouncements.isEmpty());
        //assertEquals(announcements, retrievedAnnouncements);
    }

    @Test
    public void testGetAnnouncementByIndex() {

        Announcement retrievedAnnouncement = announcementService.getAnnouncementByIndex(1);

        assertNotNull(retrievedAnnouncement);
        //assertEquals(announcement, retrievedAnnouncement);
    }

    @Test
    public void testGetAnnouncementsByName() {
        List<Announcement> retrievedAnnouncements = announcementService.getAnnouncementsByName("anmname");

        assertFalse(retrievedAnnouncements.isEmpty());
        //assertEquals(announcements, retrievedAnnouncements);
    }

    @Test
    public void testGetAnnouncementsByPeriod() {
        Date startDate = new Date();
        Date endDate = new Date();
        List<Announcement> retrievedAnnouncements = announcementService.getAnnouncementsByPeriod(startDate, endDate);

        assertFalse(retrievedAnnouncements.isEmpty());
        //assertEquals(announcement, retrievedAnnouncements);
    }

    @Test
    public void testGetAnnouncementsByEmptype() {
        List<Announcement> retrievedAnnouncements = announcementService.getAnnouncementsByEmptype("anmemptype");

        assertFalse(retrievedAnnouncements.isEmpty());
        //assertEquals(announcement, retrievedAnnouncements);
    }

    @Test
    public void testGetAnnouncementsByRecruitm() {
        List<Announcement> retrievedAnnouncements = announcementService.getAnnouncementsByRecruitm(1);

        assertFalse(retrievedAnnouncements.isEmpty());
        //assertEquals(announcements, retrievedAnnouncements);
    }

    @Test
    public void testGetAnnouncementsByCompany() {
        List<Announcement> retrievedAnnouncements = announcementService.getAnnouncementsByCompany(company);

        assertFalse(retrievedAnnouncements.isEmpty());
        //assertEquals(announcements, retrievedAnnouncements);
    }

    // 여기에 추가적인 서비스 메소드들에 대한 테스트를 구현할 수 있습니다.
}
