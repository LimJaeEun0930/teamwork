package capweb.capprac.repository;

import capweb.capprac.entity.Company;
import capweb.capprac.entity.MeetingRoom;
import capweb.capprac.entity.Mrp;
import capweb.capprac.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MrpRepositoryTest {

    @Autowired
    private MrpRepository mrpRepository;

    private Mrp mrp;
    private User user;
    private MeetingRoom meetingRoom;
    private Company company;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    void setUp() {
        // 가정: User, MeetingRoom, Company 객체가 생성되었다고 가정합니다.
        Date date = new Date();
        user = new User();
        user.setUsId("usid");
        user.setUsPw("uspw");
        user.setUsName("usname");
        user.setUsJoindate(date);
        user.setUsJoinIP("127.0.0.1");
        meetingRoom = new MeetingRoom();
        meetingRoom.setMrMrid("mrid");
        meetingRoom.setMrName("mrname");
        meetingRoom.setMrCategory("mrcategory");
        company = new Company();
        company.setCpId("cpid");
        company.setCpName("cpname");
        company.setCpPw("cppw");
        company.setCpAddr("cpaddr");
        company.setCpCategory("cpcategory");
        company.setCpMtid("cpmtid");
        company.setCpMtname("cpmtname");
        company.setCpJoindate(date);
        company.setCpJoinIP("127.0.0.1");
        userRepository.save(user);
        meetingRoomRepository.save(meetingRoom);
        companyRepository.save(company);
        // Mrp 객체의 필드를 설정합니다.
        mrp = new Mrp();
        mrp.setMrpUsid(user);
        mrp.setMrpMrid(meetingRoom);
        mrp.setMrpMtid(company);
        // 테스트 데이터를 저장합니다.
        mrpRepository.save(mrp);
    }

    @Test
    @Transactional
    //인덱스로찾기
    void whenFindByIndex_thenCorrectMrpIsReturned() {
        Mrp foundMrp = mrpRepository.findByIndex(1);
        assertNotNull(foundMrp);
    }

    @Test
    @Transactional
    //유저로찾기
    void whenFindByUser_thenCorrectMrpIsReturned() {
        List<Mrp> foundMrp = mrpRepository.findByUser(user);
      assertNotNull(foundMrp);
    }

    @Test
    @Transactional
        //유저로찾기
    void whenFindByCompany_thenCorrectMrpIsReturned() {
        List<Mrp> foundMrp = mrpRepository.findByCompany(company);
        assertNotNull(foundMrp);
    }

    @Test
    @Transactional
    void whenFindAll_thenAllMrpsAreReturned() {
        List<Mrp> mrps = mrpRepository.findAll();
        assertThat(mrps).isNotEmpty();
    }

    @Test
    @Transactional
    void whenDeleteByIndex_thenMrpIsDeleted() {
        mrpRepository.deleteByIndex(mrp.getMrpIndex());
        Mrp deletedMrp = mrpRepository.findByIndex(mrp.getMrpIndex());
        assertNull(deletedMrp);
    }
    @Test
    @Transactional
    void whenFindByUserAndMeetingRoom() {
        List<Mrp> mrps = mrpRepository.findByUserAndMeetingRoom(user,meetingRoom);
        assertThat(mrps).isNotEmpty();
    }
    @Test
    @Transactional
    void whenFindByCompanyAndMeetingRoom(){
        List<Mrp>mrps = mrpRepository.findByCompanyAndMeetingRoom(company,meetingRoom);
        assertThat(mrps).isNotEmpty();
    }
}
