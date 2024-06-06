package capweb.capprac.service;

import capweb.capprac.entity.*;
import capweb.capprac.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private MrpRepository mrpRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    private Mrp mrp;
    private User user;
    private MeetingRoom meetingRoom;
    private Company company;
    private Message message;
    private Date date;

    // 테스트 데이터 준비
    @BeforeEach
    void setUp() {
        // 가정: User, MeetingRoom, Company 객체가 생성되었다고 가정합니다.
        date = new Date();
        user = new User();
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

    //엔티티 노 세션오류(assertThar문allmatch에서 발생) :지연로딩때문에 발생하므로 발생하는메소드에다가 트랜잭션해주기
    @Test
    public void whenCreateMessage_thenMessageIsSaved() {
        String content = "Test content";
        Message savedMessage = messageService.createMessage(content, date,mrp, meetingRoom);
        assertThat(savedMessage).isNotNull();
        assertThat(savedMessage.getMsgContent()).isEqualTo(content);
        // 추가적인 검증 로직...
    }

    @Test
    public void whenFindAllMessages_thenAllMessagesAreReturned() {
        List<Message> messages = messageService.findAllMessages();
        assertThat(messages).isNotEmpty();
        // 추가적인 검증 로직...
    }

    @Test
    public void whenFindMessagesByContent_thenCorrectMessagesAreReturned() {
        String searchContent = "content";
        List<Message> messages = messageService.findMessagesByContent(searchContent);
        assertThat(messages).isNotEmpty();
        assertThat(messages).allMatch(message -> message.getMsgContent().contains(searchContent));
        // 추가적인 검증 로직...
    }

    @Test
    @Transactional
    public void whenFindMessagesBySender_thenCorrectMessagesAreReturned() {
        List<Message> messages = messageService.findMessagesBySender(mrp);
        assertThat(messages).isNotEmpty();
        assertThat(messages).allMatch(message -> message.getMsgSenderusid().equals(mrp));
        // 추가적인 검증 로직...
    }

    @Test
    @Transactional
    public void whenFindMessagesByTime_thenCorrectMessagesAreReturned() {
        List<Message> messages = messageService.findMessagesByTime(date);
        assertThat(messages).isNotEmpty();
        assertThat(messages).allMatch(message -> message.getMsgTime().equals(date));
        // 추가적인 검증 로직...
    }

    @Test
    public void whenFindMessageByIndex_thenCorrectMessageIsReturned() {
        Message testmessage = messageService.findMessageByIndex(message.getMsgIndex());
        assertThat(testmessage).isNotNull();
        assertThat(testmessage.getMsgIndex()).isEqualTo(message.getMsgIndex());
        // 추가적인 검증 로직...
    }


    // 나머지 메소드들에 대한 테스트 케이스도 비슷한 방식으로 작성합니다.
    // 예를 들어, findMessagesByContent, findMessagesByTime 등의 메소드에 대한 테스트 케이스를 추가합니다.

    // 각 테스트 케이스는 실제 데이터베이스와 상호작용하며, 테스트가 끝난 후에는 데이터베이스 상태가 롤백됩니다.


    @Test
    @Transactional
    public void whenFindMessagesByMeetingRoom_thenCorrectMessagesAreReturned() {
        //MeetingRoom specificMeetingRoom = ...; // 특정 모임방 객체 생성
        List<Message> messages = messageService.findMessagesByMeetingRoom(meetingRoom);
        assertThat(messages).isNotEmpty();
        assertThat(messages).allMatch(message -> message.getMsgMrid().equals(meetingRoom));
        // 추가적인 검증 로직...
    }

    @Test
    @Transactional
    public void whenFindMessagesBySenderAndMeetingRoom_thenCorrectMessagesAreReturned() {
        //Mrp specificSender = ...; // 특정 발신자 객체 생성
        //MeetingRoom specificMeetingRoom = ...; // 특정 모임방 객체 생성
        List<Message> messages = messageService.findMessagesBySenderAndMeetingRoom(mrp,meetingRoom);
        assertThat(messages).isNotEmpty();
        assertThat(messages).allMatch(message -> message.getMsgSenderusid().equals(mrp) && message.getMsgMrid().equals(meetingRoom));
        // 추가적인 검증 로직...
    }

    @Test
    public void whenFindMessagesByDateRange_thenCorrectMessagesAreReturned() {
        Date startDate = date; // 시작 날짜 객체 생성
        Date endDate = date; // 종료 날짜 객체 생성
        List<Message> messages = messageService.findMsgsMessagesByDateRange(startDate, endDate);
        assertThat(messages).isNotEmpty();
        // 추가적인 검증 로직...
    }

    @Test
    public void whenFindMessagesByMeetingRoomAndDateRange_thenCorrectMessagesAreReturned() {
        //MeetingRoom specificMeetingRoom = ...; // 특정 모임방 객체 생성
        Date startDate = date; // 시작 날짜 객체 생성
        Date endDate = date; // 종료 날짜 객체 생성
        List<Message> messages = messageService.findMsgsMessagesByMeetingRoomAndDateRange(meetingRoom, startDate, endDate);
        assertThat(messages).isNotEmpty();
        // 추가적인 검증 로직...
    }

    @Test
    @Transactional
    public void whenFindMessagesByMeetingRoomAndContent_thenCorrectMessagesAreReturned() {
        //MeetingRoom specificMeetingRoom = ...; // 특정 모임방 객체 생성
        String content = "content"; // 검색할 내용
        List<Message> messages = messageService.findMessagesByMeetingRoomAndContent(meetingRoom, content);
        assertThat(messages).isNotEmpty();
        assertThat(messages).allMatch(message -> message.getMsgMrid().equals(meetingRoom) && message.getMsgContent().contains(content));
        // 추가적인 검증 로직...
    }
}
