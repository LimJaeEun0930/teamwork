package capweb.capprac;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
public class MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;

    public MeetingRoomService(MeetingRoomRepository meetingRoomRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
    }

    public MeetingRoom createMeetingRoom(String mrMrid, String mrName, String mrCategory) {
        // 필수값 체크
        if (mrMrid == null || mrMrid.trim().isEmpty()) {
            throw new IllegalArgumentException("Meeting room ID cannot be null or empty.");
        }
        if (mrName == null || mrName.trim().isEmpty()) {
            throw new IllegalArgumentException("Meeting room name cannot be null or empty.");
        }
        if (mrCategory == null || mrCategory.trim().isEmpty()) {
            throw new IllegalArgumentException("Meeting room category cannot be null or empty.");
        }

        // 중복 체크
        if (meetingRoomRepository.findMeetingRoomByMrid(mrMrid) != null ||
                meetingRoomRepository.findMeetingRoomByName(mrName) != null) {
            throw new IllegalStateException("A meeting room with the same ID or name already exists.");
        }

        // 새로운 MeetingRoom 객체 생성 및 저장
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setMrMrid(mrMrid);
        meetingRoom.setMrName(mrName);
        meetingRoom.setMrCategory(mrCategory);
        meetingRoomRepository.save(meetingRoom);
        return meetingRoom;
    }


    // 삭제 - mrIndex로 MeetingRoom 삭제
    public boolean deleteMeetingRoom(int mrIndex) {
        MeetingRoom meetingRoom = meetingRoomRepository.findMeetingRoomByIndex(mrIndex);
        if (meetingRoom != null) {
            meetingRoomRepository.deleteByIndex(mrIndex);
            return true; // 삭제 성공
        }
        return false; // 회의실이 없으면 삭제 실패
    }

    // 수정 - mrName과 mrCategory만 수정 가능
    public boolean updateMeetingRoom(int mrIndex, String newMrName, String newMrCategory) {
        MeetingRoom meetingRoom = meetingRoomRepository.findMeetingRoomByIndex(mrIndex);
        if (meetingRoom == null) {
            return false; // 회의실이 없으면 수정 실패
        }
        // 이름 중복 체크
        MeetingRoom existingMeetingRoomWithName = meetingRoomRepository.findMeetingRoomByName(newMrName);
        if (existingMeetingRoomWithName != null && existingMeetingRoomWithName.getMrIndex() != mrIndex) {
            return false; // 다른 회의실에서 이미 사용 중인 이름이면 수정 실패
        }
        // 수정 가능한 필드 업데이트
        meetingRoom.setMrName(newMrName);
        meetingRoom.setMrCategory(newMrCategory);
        meetingRoomRepository.update(meetingRoom);
        return true; // 수정 성공
    }
    // 카테고리로 MeetingRoom 찾기
    public List<MeetingRoom> findMeetingRoomsByCategory(String mrCategory) {
        return meetingRoomRepository.findMeetingRoomsByCategory(mrCategory);
    }
    // 모든 MeetingRoom 찾기
    public List<MeetingRoom> findAllMeetingRooms() {
        return meetingRoomRepository.findAllMeetingRooms();
    }
    // 모든 MeetingRoom의 카테고리 리스트 반환
    public List<String> findAllMeetingRoomCategories() {
        return meetingRoomRepository.findAllMeetingRooms().stream()
                .map(MeetingRoom::getMrCategory)
                .distinct()
                .collect(Collectors.toList());
    }
    // 추가적인 서비스 메소드들...
}
