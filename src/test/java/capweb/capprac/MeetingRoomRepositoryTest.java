package capweb.capprac;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MeetingRoomRepositoryTest {

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

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
    @Transactional
    public void testSave() {
        MeetingRoom meetingRoom = new MeetingRoom(); // 생성자를 가정
        meetingRoom.setMrMrid("testmrid");
        meetingRoom.setMrName("testmrname");
        meetingRoom.setMrCategory("testmrcategory");
        // meetingRoom 속성 설정
        meetingRoomRepository.save(meetingRoom);
        assertNotNull(meetingRoom.getMrMrid()); // getId()가 생성된 ID를 검색하는 방법이라고 가정
    }

    @Test
    public void testFindMeetingRoomByIndex() {
        // 알려진 인덱스로 저장된 meetingRoom이 있다고 가정
        MeetingRoom found = meetingRoomRepository.findMeetingRoomByIndex(1); // 예시 인덱스
        assertNotNull(found);
    }

    @Test
    public void testFindAllMeetingRooms() {
        List<MeetingRoom> meetingRooms = meetingRoomRepository.findAllMeetingRooms();
        assertFalse(meetingRooms.isEmpty());
    }

    @Test
    public void testUpdate() {
        MeetingRoom meetingRoom = new MeetingRoom(); // 생성자를 가정
        meetingRoom.setMrMrid("testmrid");
        meetingRoom.setMrName("testmrname");
        meetingRoom.setMrCategory("testmrcategory");
        // meetingRoom 속성 설정
        meetingRoomRepository.save(meetingRoom);
        // 업데이트할 meetingRoom이 있다고 가정
        MeetingRoom exmeetingRoom = meetingRoomRepository.findMeetingRoomByIndex(1); // 예시 인덱스
        // 일부 속성 수정
        exmeetingRoom.setMrName("새 회의실 이름"); // setName() 메소드가 있다고 가정
        meetingRoomRepository.update(exmeetingRoom);
        // 다시 가져와서 변경 사항 확인
        MeetingRoom updatedRoom = meetingRoomRepository.findMeetingRoomByIndex(1);
        assertEquals("새 회의실 이름", updatedRoom.getMrName());
    }

    @Test
    public void testDeleteByIndex() {
        // meetingRoom을 생성하고 삭제
        MeetingRoom meetingRoom = new MeetingRoom(); // 생성자를 가정
        meetingRoom.setMrMrid("testmrid");
        meetingRoom.setMrName("testmrname");
        meetingRoom.setMrCategory("testmrcategory");
        meetingRoomRepository.save(meetingRoom);
        int id = meetingRoom.getMrIndex(); // ID 가져오기
        meetingRoomRepository.deleteByIndex(id);
        assertNull(meetingRoomRepository.findMeetingRoomByIndex(id));
    }

    @Test
    public void testFindMeetingRoomByMrid() {
        // 알려진 MRID로 저장된 meetingRoom이 있다고 가정
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setMrMrid("MR002");
        meetingRoom.setMrName("testmrname2");
        meetingRoom.setMrCategory("testmrcategory2");
        meetingRoomRepository.save(meetingRoom);
        List<MeetingRoom> found = meetingRoomRepository.findMeetingRoomByMrid("MR002"); // 예시 MRID
        assertNotNull(found);
    }

    @Test
    public void testFindMeetingRoomByName() {
        // 알려진 이름으로 저장된 meetingRoom이 있다고 가정
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setMrMrid("testmrid");
        meetingRoom.setMrName("회의실2");
        meetingRoom.setMrCategory("testmrcategory");
        meetingRoomRepository.save(meetingRoom);
        List<MeetingRoom> found = meetingRoomRepository.findMeetingRoomByName("회의실1"); // 예시 이름
        assertNotNull(found);
    }

    @Test
    public void testFindMeetingRoomsByCategory() {
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setMrMrid("testmrid3");
        meetingRoom.setMrName("testmrname3");
        meetingRoom.setMrCategory("대형");
        meetingRoomRepository.save(meetingRoom);
        // 알려진 카테고리로 저장된 meetingRooms이 있다고 가정
        List<MeetingRoom> found = meetingRoomRepository.findMeetingRoomsByCategory("대형"); // 예시 카테고리
        assertFalse(found.isEmpty());
    }

    // 추가적인 메소드들에 대한 테스트를 여기에 구현할 수 있습니다.
}
