package capweb.capprac.service;

import capweb.capprac.entity.MeetingRoom;
import capweb.capprac.repository.MeetingRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MeetingRoomServiceTest {

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private MeetingRoomService meetingRoomService;

    @BeforeEach
    public void setUp() {
        // 테스트 데이터 준비
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setMrMrid("MR001");
        meetingRoom.setMrName("회의실1");
        meetingRoom.setMrCategory("대형");
        meetingRoomRepository.save(meetingRoom);
    }

    @Test
    public void testCreateMeetingRoom() {
        // 테스트 실행
        MeetingRoom created = meetingRoomService.createMeetingRoom("MR002", "회의실2", "중형");

        // 검증
        assertNotNull(created);
        assertEquals("MR002", created.getMrMrid());
        assertEquals("회의실2", created.getMrName());
        assertEquals("중형", created.getMrCategory());
    }

    // ... 기존 코드 ...

    @Test
    public void testDeleteMeetingRoom() {
        // 삭제할 회의실이 존재하는지 확인
        assertNotNull(meetingRoomRepository.findMeetingRoomByIndex(1));

        // 테스트 실행
        boolean result = meetingRoomService.deleteMeetingRoom(1);

        // 검증
        assertTrue(result);
        assertNull(meetingRoomRepository.findMeetingRoomByIndex(1));
    }

    @Test
    public void testUpdateMeetingRoom() {
        // 테스트 실행
        boolean result = meetingRoomService.updateMeetingRoom(1, "새 회의실 이름", "중형");

        // 검증
        assertTrue(result);
        MeetingRoom updated = meetingRoomRepository.findMeetingRoomByIndex(1);
        assertNotNull(updated);
        assertEquals("새 회의실 이름", updated.getMrName());
        assertEquals("중형", updated.getMrCategory());
    }

    @Test
    public void testFindMeetingRoomsByCategory() {
        // 테스트 실행
        List<MeetingRoom> result = meetingRoomService.findMeetingRoomsByCategory("대형");

        // 검증
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(mr -> "대형".equals(mr.getMrCategory())));
    }

    @Test
    public void testFindAllMeetingRooms() {
        // 테스트 실행
        List<MeetingRoom> result = meetingRoomService.findAllMeetingRooms();

        // 검증
        assertFalse(result.isEmpty());
    }

    @Test
    public void testFindAllMeetingRoomCategories() {
        // 테스트 실행
        List<String> result = meetingRoomService.findAllMeetingRoomCategories();

        // 검증
        assertFalse(result.isEmpty());
        assertTrue(result.contains("대형"));
    }

    // 나머지 테스트 메소드들도 이와 유사하게 수정할 수 있습니다.
}
