package capweb.capprac;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class MeetingRoomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 MeetingRoom 저장
    public void save(MeetingRoom meetingRoom) {
        entityManager.persist(meetingRoom);
    }

    // Read - mrIndex로 MeetingRoom 찾기
    public MeetingRoom findMeetingRoomByIndex(int mrIndex) {
        return entityManager.find(MeetingRoom.class, mrIndex);
    }

    // Read - 모든 MeetingRoom 찾기
    public List<MeetingRoom> findAllMeetingRooms() {
        TypedQuery<MeetingRoom> query = entityManager.createQuery("SELECT m FROM MeetingRoom m", MeetingRoom.class);
        return query.getResultList();
    }

    // Update - MeetingRoom 업데이트
    public void update(MeetingRoom meetingRoom) {
        entityManager.merge(meetingRoom);
    }

    // Delete - mrIndex로 MeetingRoom 삭제
    public void deleteByIndex(int mrIndex) {
        MeetingRoom meetingRoom = findMeetingRoomByIndex(mrIndex);
        if (meetingRoom != null) {
            entityManager.remove(meetingRoom);
        }
    }

    // Read - mrMrid로 MeetingRoom 찾기
    public MeetingRoom findMeetingRoomByMrid(String mrMrid) {
        TypedQuery<MeetingRoom> query = entityManager.createQuery(
                "SELECT m FROM MeetingRoom m WHERE m.mrMrid = :mrMrid", MeetingRoom.class);
        query.setParameter("mrMrid", mrMrid);
        return query.getSingleResult();
    }

    // Read - mrName으로 MeetingRoom 찾기
    public MeetingRoom findMeetingRoomByName(String mrName) {
        TypedQuery<MeetingRoom> query = entityManager.createQuery(
                "SELECT m FROM MeetingRoom m WHERE m.mrName = :mrName", MeetingRoom.class);
        query.setParameter("mrName", mrName);
        return query.getSingleResult(); // 단일 객체 반환
    }

    // Read - mrCategory로 MeetingRoom 찾기
    public List<MeetingRoom> findMeetingRoomsByCategory(String mrCategory) {
        TypedQuery<MeetingRoom> query = entityManager.createQuery(
                "SELECT m FROM MeetingRoom m WHERE m.mrCategory = :mrCategory", MeetingRoom.class);
        query.setParameter("mrCategory", mrCategory);
        return query.getResultList();
    }

    // 추가적인 메소드들을 여기에 구현할 수 있습니다.
}
