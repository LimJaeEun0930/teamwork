package capweb.capprac.repository;

import capweb.capprac.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageRepositoryTest {

    @Autowired
    private USerRepository userRepository;
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MrpRepository mrpRepository;
    @Autowired
    private MessageRepository messageRepository;

    private Mrp mrp;
    private USer user;
    private MeetingRoom meetingRoom;
    private Company company;
    private Message message;
    private Date date;
    @BeforeEach
    public void setUp() {
        // 가정: User, MeetingRoom, Company 객체가 생성되었다고 가정합니다.
        date = new Date();
        user = new USer();
        user.setUsId("usid");
        user.setUsPw("uspw");
        user.setUsName("usname");
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
        message = new Message();
        message.setMsgContent("content");
        message.setMsgTime(date);
        message.setMsgMrid(meetingRoom);
        message.setMsgSenderusid(mrp);
        messageRepository.save(message);
    }

    @Test
    @Transactional
    public void whenFindMessageByIndex_thenCorrectMessageIsReturned() {
        // 테스트 데이터베이스에 존재하는 메시지 인덱스를 사용하세요.
        int testMsgIndex = 1; // 예시 인덱스
        Message foundMessage = messageRepository.findMessageByIndex(testMsgIndex);
        assertThat(foundMessage).isNotNull();
        assertThat(foundMessage.getMsgIndex()).isEqualTo(testMsgIndex);
    }

    @Test
    @Transactional
    public void whenFindAllMessages_thenAllMessagesAreReturned() {
        List<Message> messages = messageRepository.findAllMessages();
        assertThat(messages).isNotEmpty();
    }

    @Test
    @Transactional
    public void whenFindMessagesByContent_thenCorrectMessagesAreReturned() {
        String testContent = "content";
        List<Message> messages = messageRepository.findMessagesByContent(testContent);
        assertThat(messages).isNotEmpty();
        assertThat(messages).allMatch(message -> message.getMsgContent().equals(testContent));
    }

    @Test
    @Transactional
    public void whenFindMessagesByTime_thenCorrectMessagesAreReturned() {
        //Date testTime = new Date(); // 테스트에 적절한 날짜를 설정하세요.
        List<Message> messages = messageRepository.findMessagesByTime(date);
        assertThat(messages).isNotEmpty();
        assertThat(messages).allMatch(message -> message.getMsgTime().equals(date));
    }

    @Test
    @Transactional
    public void whenFindMessagesBySender_thenCorrectMessagesAreReturned() {
        //Mrp testSender = new Mrp(); // 테스트에 적절한 Mrp 객체를 설정하세요.
        List<Message> messages = messageRepository.findMessagesBySender(mrp);
        assertThat(messages).isNotEmpty();
        assertThat(messages).allMatch(message -> message.getMsgSenderusid().equals(mrp));
    }

    @Test
    @Transactional
    public void whenFindMessagesByMeetingRoom_thenCorrectMessagesAreReturned() {
        //MeetingRoom testMeetingRoom = new MeetingRoom(); // 테스트에 적절한 MeetingRoom 객체를 설정하세요.
        List<Message> messages = messageRepository.findMessagesByMeetingRoom(meetingRoom);
        assertThat(messages).isNotEmpty();
        assertThat(messages).allMatch(message -> message.getMsgMrid().equals(meetingRoom));
    }

    @Test
    @Transactional
    public void whenFindMessagesBySenderAndMeetingRoom_thenCorrectMessagesAreReturned() {
        //Mrp testSender = new Mrp(); // 테스트에 적절한 Mrp 객체를 설정하세요.
        //MeetingRoom testMeetingRoom = new MeetingRoom(); // 테스트에 적절한 MeetingRoom 객체를 설정하세요.
        List<Message> messages = messageRepository.findMessagesBySenderAndMeetingRoom(mrp,meetingRoom);
        assertThat(messages).isNotEmpty();
        assertThat(messages).allMatch(message -> message.getMsgSenderusid().equals(mrp) && message.getMsgMrid().equals(meetingRoom));
    }

    @Test
    @Transactional
    public void whenFindMessagesByDateRange_thenCorrectMessagesAreReturned() {
        Date startDate = date; // 테스트에 적절한 시작 날짜를 설정하세요.
        Date endDate = date; // 테스트에 적절한 종료 날짜를 설정하세요.
        List<Message> messages = messageRepository.findMessagesByDateRange(startDate, endDate);
        assertThat(messages).isNotEmpty();
        assertThat(messages).allMatch(message -> !message.getMsgTime().before(startDate) && !message.getMsgTime().after(endDate));
    }

    @Test
    @Transactional
    public void whenFindMessagesByMeetingRoomAndDateRange_thenCorrectMessagesAreReturned() {
        //MeetingRoom testMeetingRoom = new MeetingRoom(); // 테스트에 적절한 MeetingRoom 객체를 설정하세요.
        Date startDate = date; // 테스트에 적절한 시작 날짜를 설정하세요.
        Date endDate = date; // 테스트에 적절한 종료 날짜를 설정하세요.
        List<Message> messages = messageRepository.findMessagesByMeetingRoomAndDateRange(meetingRoom, startDate, endDate);
        assertThat(messages).isNotEmpty();
        assertThat(messages).allMatch(message -> message.getMsgMrid().equals(meetingRoom) && !message.getMsgTime().before(startDate) && !message.getMsgTime().after(endDate));
    }
    @Test
    @Transactional
    public void whenFindMessagesByMeetingRoomAndContent_thenCorrectMessagesAreReturned() {
        String testContent = "content";
        List<Message> messages = messageRepository.findMessagesByMeetingRoomAndContent(meetingRoom,testContent);

        // 검증
        assertThat(messages).isNotEmpty();
        assertThat(messages).contains(message);
    }
}
