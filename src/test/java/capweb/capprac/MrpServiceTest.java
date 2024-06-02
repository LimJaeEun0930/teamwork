package capweb.capprac;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MrpServiceTest {

    @Autowired
    private MrpRepository mrpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private MrpService mrpService;

    private Mrp mrp;
    private User user;
    private MeetingRoom meetingRoom;
    private Company company;

    @BeforeEach
    void setUp() {
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
    void whenCreateMrp_thenMrpIsCreated() {
        Date update = new Date();
        User upuser;
        upuser = new User();
        upuser.setUsId("upusid");
        upuser.setUsPw("upuspw");
        upuser.setUsName("upusname");
        upuser.setUsJoindate(update);
        upuser.setUsJoinIP("123.0.0.1");
        userRepository.save(upuser);
        //Mrp createdMrp = mrpService.createMrp(user, meetingRoom, company);

        //assertThat(createdMrp).isNotNull();
        //assertThat(createdMrp.getMrpUsid()).isEqualTo(user);
        //assertThat(createdMrp.getMrpMrid()).isEqualTo(meetingRoom);
        //assertThat(createdMrp.getMrpMtid()).isEqualTo(company);

        Mrp createdMrp = mrpService.createMrp(upuser, meetingRoom, company);
        assertThat(createdMrp).isNotNull();
        assertThat(createdMrp.getMrpUsid()).isEqualTo(upuser);
        assertThat(createdMrp.getMrpMrid()).isEqualTo(meetingRoom);
        assertThat(createdMrp.getMrpMtid()).isEqualTo(company);

    }

    @Test
    void whenDeleteMrp_thenMrpIsDeleted() {
        int mrpIndex = mrp.getMrpIndex();
        mrpService.deleteMrp(mrpIndex);

        assertThat(mrpRepository.findByIndex(mrpIndex)).isNull();
    }

    @Test
    void whenFindAllMrps_thenAllMrpsAreReturned() {
        List<Mrp> mrps = mrpService.findAllMrps();

        assertThat(mrps).isNotEmpty();
        assertThat(mrps.size()).isGreaterThanOrEqualTo(1);
    }
    @Test
    void whenFindMrpsByUser(){
        List<Mrp>mrps =  mrpRepository.findByUser(user);
        assertThat(mrps).isNotEmpty();
    }
    @Test
    void whenFindMrpsByMeetingRoom(){
        List<Mrp>mrps =  mrpRepository.findByMeetingRoom(meetingRoom);
        assertThat(mrps).isNotEmpty();
    }
    @Test
    void whenFindMrpsByCompany(){
        List<Mrp>mrps =  mrpRepository.findByCompany(company);
        assertThat(mrps).isNotEmpty();
    }
    @Test
    void whenFindMrpsByUserAndMeetingRoom(){
        List<Mrp>mrps = mrpRepository.findByUserAndMeetingRoom(user,meetingRoom);
        assertThat(mrps).isNotEmpty();
    }
    @Test
    void whenFindMrpsByCompanyAndMeetingRoom(){
        List<Mrp>mrps =  mrpRepository.findByCompanyAndMeetingRoom(company,meetingRoom);
        assertThat(mrps).isNotEmpty();
    }

    @Test
    void whenCountParticipantsInMeetingRoom_thenCorrectCountIsReturned() {
        int count = mrpService.countParticipantsInMeetingRoom(meetingRoom);

        assertEquals(2, count); // 실제 참가자 수 확인
    }


    // 나머지 메소드들에 대한 테스트 케이스도 비슷한 방식으로 작성할 수 있습니다.

    @Test
    public void givenValidUsidAndMrid_whenDeleteMrpProcess_thenTrue() {
        // 테스트에 필요한 데이터 준비
        String usid = user.getUsId();

        String mrid = meetingRoom.getMrMrid();

        // 삭제 프로세스 실행
        boolean result = mrpService.deleteMrpProcess(usid, mrid);

        // 검증
        assertTrue(result);
    }

    @Test
    public void givenInvalidUsidOrMrid_whenDeleteMrpProcess_thenFalse() {
        // 잘못된 usid와 mrid 설정
        String usid = "nonExistingUsid";
        String mrid = "nonExistingMrid";

        // 삭제 프로세스 실행
        boolean result = mrpService.deleteMrpProcess(usid, mrid);

        // 검증
        assertFalse(result);
    }
    @Test
    public void givenValidMtidAndMrid_whenCdeleteMrpProcess_thenTrue() {
        // 테스트에 필요한 데이터 준비
        String mtid = company.getCpMtid();
        String mrid = meetingRoom.getMrMrid();
        // 삭제 프로세스 실행
        boolean result = mrpService.cdeleteMrpProcess(mtid, mrid);
        // 검증
        assertTrue(result);
    }

    @Test
    @Transactional
    public void givenInvalidMtidOrMrid_whenCdeleteMrpProcess_thenFalse() {
        // 잘못된 mtid와 mrid 설정
        String mtid = "nonExistingMtid";
        String mrid = "nonExistingMrid";
        // 삭제 프로세스 실행
        boolean result = mrpService.cdeleteMrpProcess(mtid, mrid);

        // 검증
        assertFalse(result);
    }
}
