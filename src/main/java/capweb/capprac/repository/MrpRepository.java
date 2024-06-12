package capweb.capprac.repository;
//중요!!!!
//매개변수로 엔티티(User) usid 등 되어있는것들은 실제 User의 usid를 가져오는것이 아닌 User객체를 가져오는것
//User의 usid를 가져오려면 매개변수가 String usid가 되어야한다.
//외래키를 이용하려면 필드가 아닌 객체를 이용해야함
//필드를 이용할때는 매개변수로 String타입임
import capweb.capprac.entity.Company;
import capweb.capprac.entity.MeetingRoom;
import capweb.capprac.entity.Mrp;
import capweb.capprac.entity.USer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class MrpRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 Mrp 저장
    @Transactional
    public void save(Mrp mrp) {
        entityManager.persist(mrp);
    }

    // Read - mrpIndex로 Mrp 찾기
    public Mrp findByIndex(int mrpIndex) {
        return entityManager.find(Mrp.class, mrpIndex);
    }

    // Update - Mrp 업데이트
    @Transactional
    public void update(Mrp mrp) {
        entityManager.merge(mrp);
    }

    // Delete - mrpIndex로 Mrp 삭제
    @Transactional
    public void deleteByIndex(int mrpIndex) {
        Mrp mrp = findByIndex(mrpIndex);
        if (mrp != null) {
            entityManager.remove(mrp);
        }
    }

    // Read - 모든 Mrp 찾기
    public List<Mrp> findAll() {
        return entityManager.createQuery("SELECT m FROM Mrp m", Mrp.class).getResultList();
    }

    // Read - 사용자입력받아 Mrp찾기
    public List<Mrp> findByUser(USer user) {
        return entityManager.createQuery("SELECT m FROM Mrp m WHERE m.mrpUsid = :user", Mrp.class)
                .setParameter("user", user)
                .getResultList();
    }
    // Read - 모임방입력받아 Mrp찾기
    public List<Mrp> findByMeetingRoom(MeetingRoom meetingRoom) {
        return entityManager.createQuery("SELECT m FROM Mrp m WHERE m.mrpMrid = :meetingRoom", Mrp.class)
                .setParameter("meetingRoom", meetingRoom)
                .getResultList();
    }

    // Read - 회사입력받아 Mrp찾기
    public List<Mrp> findByCompany(Company company) {
        return entityManager.createQuery("SELECT m FROM Mrp m WHERE m.mrpMtid = :company", Mrp.class)
                .setParameter("company", company)
                .getResultList();
    }

    // Read - 사용자와 모임방 입력받아 해당 Mrp찾기 결과는 하나만
    public List<Mrp> findByUserAndMeetingRoom(USer user, MeetingRoom meetingRoom) {
        return entityManager.createQuery("SELECT m FROM Mrp m WHERE m.mrpUsid = :user AND m.mrpMrid = :meetingRoom", Mrp.class)
                .setParameter("user", user)
                .setParameter("meetingRoom", meetingRoom)
                .getResultList();
    }
    // Read - 특정 멘토와 모임방에 동시에 속한 Mrp 찾기 결과는 하나만
    public List<Mrp> findByCompanyAndMeetingRoom(Company company, MeetingRoom meetingRoom) {
        return entityManager.createQuery(
                        "SELECT m FROM Mrp m WHERE m.mrpMtid = :company AND m.mrpMrid = :meetingRoom", Mrp.class)
                .setParameter("company", company)
                .setParameter("meetingRoom", meetingRoom)
                .getResultList();
    }
    // Read - 모임방을 입력받아 멘토가 들어있는 방 찾기
    public List<Mrp> findByMeetingRoomAndMentor(MeetingRoom meetingRoom) {
        return entityManager.createQuery(
                        "SELECT m FROM Mrp m WHERE m.mrpMrid = :meetingRoom AND m.mrpMtid IS NOT NULL", Mrp.class)
                .setParameter("meetingRoom", meetingRoom)
                .getResultList();
    }
    // 추가적인 조합 검색 메소드를 여기에 구현할 수 있습니다.
}
