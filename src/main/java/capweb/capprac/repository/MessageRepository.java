package capweb.capprac.repository;
//중요!!!!
//매개변수로 엔티티(User) usid 등 되어있는것들은 실제 User의 usid를 가져오는것이 아닌 User객체를 가져오는것
//User의 usid를 가져오려면 매개변수가 String usid가 되어야한다.
//외래키를 이용하려면 필드가 아닌 객체를 이용해야함
//필드를 이용할때는 매개변수로 String타입임
import capweb.capprac.entity.MeetingRoom;
import capweb.capprac.entity.Message;
import capweb.capprac.entity.Mrp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Repository
public class MessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 Message 저장
    @Transactional
    public void save(Message message) {
        entityManager.persist(message);
    }

    // Read - msgIndex로 Message 찾기
    public Message findMessageByIndex(int msgIndex) {
        return entityManager.find(Message.class, msgIndex);
    }

    // Read - 모든 Message 찾기
    public List<Message> findAllMessages() {
        TypedQuery<Message> query = entityManager.createQuery("SELECT m FROM Message m", Message.class);
        return query.getResultList();
    }

    // Update - Message 업데이트
    @Transactional
    public void update(Message message) {
        entityManager.merge(message);
    }

    // Delete - msgIndex로 Message 삭제
    @Transactional
    public void deleteByIndex(int msgIndex) {
        Message message = findMessageByIndex(msgIndex);
        if (message != null) {
            entityManager.remove(message);
        }
    }

    // Read - msgContent로 Message 찾기
    public List<Message> findMessagesByContent(String msgContent) {
        TypedQuery<Message> query = entityManager.createQuery(
                "SELECT m FROM Message m WHERE m.msgContent = :msgContent", Message.class);
        query.setParameter("msgContent", msgContent);
        return query.getResultList();
    }

    // Read - msgTime으로 Message 찾기
    public List<Message> findMessagesByTime(Date msgTime) {
        TypedQuery<Message> query = entityManager.createQuery(
                "SELECT m FROM Message m WHERE m.msgTime = :msgTime", Message.class);
        query.setParameter("msgTime", msgTime);
        return query.getResultList();
    }

    // Read - msgSenderusid로 Message 찾기
    public List<Message> findMessagesBySender(Mrp msgSenderusid) {
        TypedQuery<Message> query = entityManager.createQuery(
                "SELECT m FROM Message m WHERE m.msgSenderusid = :msgSenderusid", Message.class);
        query.setParameter("msgSenderusid", msgSenderusid);
        return query.getResultList();
    }

    // Read - msgMrid로 Message 찾기
    public List<Message> findMessagesByMeetingRoom(MeetingRoom msgMrid) {
        TypedQuery<Message> query = entityManager.createQuery(
                "SELECT m FROM Message m WHERE m.msgMrid = :msgMrid", Message.class);
        query.setParameter("msgMrid", msgMrid);
        return query.getResultList();
    }

    // Read - 조합된 조건으로 Message 찾기
    public List<Message> findMessagesBySenderAndMeetingRoom(Mrp msgSenderusid, MeetingRoom msgMrid) {
        TypedQuery<Message> query = entityManager.createQuery(
                "SELECT m FROM Message m WHERE m.msgSenderusid = :msgSenderusid AND m.msgMrid = :msgMrid", Message.class);
        query.setParameter("msgSenderusid", msgSenderusid);
        query.setParameter("msgMrid", msgMrid);
        return query.getResultList();
    }
    // Read - 특정 날짜 기간에 해당하는 Message 찾기
    public List<Message> findMessagesByDateRange(Date startDate, Date endDate) {
        return entityManager.createQuery(
                        "SELECT m FROM Message m WHERE m.msgTime BETWEEN :startDate AND :endDate", Message.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
    // Read - 특정 모임방과 날짜 기간에 해당하는 메시지를 찾기
    public List<Message> findMessagesByMeetingRoomAndDateRange(MeetingRoom meetingRoom, Date startDate, Date endDate) {
        TypedQuery<Message> query = entityManager.createQuery(
                "SELECT m FROM Message m WHERE m.msgMrid = :meetingRoom AND m.msgTime BETWEEN :startDate AND :endDate", Message.class);
        query.setParameter("meetingRoom", meetingRoom);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }


    // Read - msgMrid와 msgContent로 Message 찾기
    public List<Message> findMessagesByMeetingRoomAndContent(MeetingRoom msgMrid, String msgContent) {
        TypedQuery<Message> query = entityManager.createQuery(
                "SELECT m FROM Message m WHERE m.msgMrid = :msgMrid AND m.msgContent LIKE :msgContent", Message.class);
        query.setParameter("msgMrid", msgMrid);
        query.setParameter("msgContent", "%" + msgContent + "%");
        return query.getResultList();
    }

    // 추가적인 메소드들을 여기에 구현할 수 있습니다.
}
