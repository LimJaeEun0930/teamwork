package capweb.capprac.repository;
//중요!!!!
//매개변수로 엔티티(User) usid 등 되어있는것들은 실제 User의 usid를 가져오는것이 아닌 User객체를 가져오는것
//User의 usid를 가져오려면 매개변수가 String usid가 되어야한다.
//외래키를 이용하려면 필드가 아닌 객체를 이용해야함
//필드를 이용할때는 매개변수로 String타입임
import capweb.capprac.entity.MeetingRoom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class MeetingRoomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 MeetingRoom 저장
    @Transactional
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
    @Transactional
    public void update(MeetingRoom meetingRoom) {
        entityManager.merge(meetingRoom);
    }

    // Delete - mrIndex로 MeetingRoom 삭제
    @Transactional
    public void deleteByIndex(int mrIndex) {
        MeetingRoom meetingRoom = findMeetingRoomByIndex(mrIndex);
        if (meetingRoom != null) {
            entityManager.remove(meetingRoom);
        }
    }

    // Read - mrMrid로 MeetingRoom 찾기
    public List<MeetingRoom> findMeetingRoomByMrid(String mrMrid) {
        TypedQuery<MeetingRoom> query = entityManager.createQuery(
                "SELECT m FROM MeetingRoom m WHERE m.mrMrid = :mrMrid", MeetingRoom.class);
        query.setParameter("mrMrid", mrMrid);
        return query.getResultList();
    }

    // Read - mrName으로 MeetingRoom 찾기
    public List<MeetingRoom> findMeetingRoomByName(String mrName) {
        TypedQuery<MeetingRoom> query = entityManager.createQuery(
                "SELECT m FROM MeetingRoom m WHERE m.mrName = :mrName", MeetingRoom.class);
        query.setParameter("mrName", mrName);
        return query.getResultList(); // 단일 객체 반환
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
