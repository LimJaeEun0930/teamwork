package capweb.capprac;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    // 만들기 - 새로운 Message 생성 및 저장
    @Transactional
    public Message createMessage(String content, Date time, Mrp sender, MeetingRoom meetingRoom) {
        // 필수값 체크
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty.");
        }
        if (time == null) {
            throw new IllegalArgumentException("Message time cannot be null.");
        }
        if (sender == null) {
            throw new IllegalArgumentException("Message sender cannot be null.");
        }
        if (meetingRoom == null) {
            throw new IllegalArgumentException("Message meeting room cannot be null.");
        }

        Message message = new Message();
        message.setMsgContent(content);
        message.setMsgTime(time);
        message.setMsgSenderusid(sender);
        message.setMsgMrid(meetingRoom);
        messageRepository.save(message);
        return message;
    }

    public Message findMessageByIndex(int msgIndex) {
        return messageRepository.findMessageByIndex(msgIndex);
    }
    // 조회 - 모든 Message 찾기
    public List<Message> findAllMessages() {
        return messageRepository.findAllMessages();
    }

    // 조회 - 특정 내용으로 Message 찾기
    public List<Message> findMessagesByContent(String content) {
        return messageRepository.findMessagesByContent(content);
    }

    // 조회 - 특정 시간으로 Message 찾기
    public List<Message> findMessagesByTime(Date time) {
        return messageRepository.findMessagesByTime(time);
    }

    // 조회 - 특정 발신자로 Message 찾기
    public List<Message> findMessagesBySender(Mrp sender) {
        return messageRepository.findMessagesBySender(sender);
    }

    // 조회 - 특정 모임방으로 Message 찾기
    public List<Message> findMessagesByMeetingRoom(MeetingRoom meetingRoom) {
        return messageRepository.findMessagesByMeetingRoom(meetingRoom);
    }

    // 조회 - 특정 발신자와 모임방으로 Message 찾기
    public List<Message> findMessagesBySenderAndMeetingRoom(Mrp sender, MeetingRoom meetingRoom) {
        return messageRepository.findMessagesBySenderAndMeetingRoom(sender, meetingRoom);
    }

    // 조회 - 특정 날짜 기간에 해당하는 Message 찾기
    public List<Message> findMsgsMessagesByDateRange(Date startDate, Date endDate) {
        return messageRepository.findMessagesByDateRange(startDate, endDate);
    }
    // 조회 - 특정 모임방과 날짜 기간에 해당하는 메시지 찾기
    public List<Message> findMsgsMessagesByMeetingRoomAndDateRange(MeetingRoom meetingRoom, Date startDate, Date endDate) {
        return messageRepository.findMessagesByMeetingRoomAndDateRange(meetingRoom, startDate, endDate);
    }

    // 조회 - 특정 모임방과 내용으로 Message 찾기
    public List<Message> findMessagesByMeetingRoomAndContent(MeetingRoom meetingRoom, String content) {
        return messageRepository.findMessagesByMeetingRoomAndContent(meetingRoom, content);
    }

    // 추가적인 서비스 메소드들...
}
