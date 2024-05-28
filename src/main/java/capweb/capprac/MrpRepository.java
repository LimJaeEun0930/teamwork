package capweb.capprac;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class MrpRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 Mrp 저장
    public void save(Mrp mrp) {
        entityManager.persist(mrp);
    }

    // Read - mrpIndex로 Mrp 찾기
    public Mrp findByIndex(int mrpIndex) {
        return entityManager.find(Mrp.class, mrpIndex);
    }

    // Update - Mrp 업데이트
    public void update(Mrp mrp) {
        entityManager.merge(mrp);
    }

    // Delete - mrpIndex로 Mrp 삭제
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

    // 멤버 변수를 이용한 검색 메소드
    public List<Mrp> findByUser(User user) {
        return entityManager.createQuery("SELECT m FROM Mrp m WHERE m.mrpUsid = :user", Mrp.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Mrp> findByMeetingRoom(MeetingRoom meetingRoom) {
        return entityManager.createQuery("SELECT m FROM Mrp m WHERE m.mrpMrid = :meetingRoom", Mrp.class)
                .setParameter("meetingRoom", meetingRoom)
                .getResultList();
    }

    public List<Mrp> findByCompany(Company company) {
        return entityManager.createQuery("SELECT m FROM Mrp m WHERE m.mrpMtid = :company", Mrp.class)
                .setParameter("company", company)
                .getResultList();
    }

    // 조합 검색 메소드
    public List<Mrp> findByUserAndMeetingRoom(User user, MeetingRoom meetingRoom) {
        return entityManager.createQuery("SELECT m FROM Mrp m WHERE m.mrpUsid = :user AND m.mrpMrid = :meetingRoom", Mrp.class)
                .setParameter("user", user)
                .setParameter("meetingRoom", meetingRoom)
                .getResultList();
    }
    // 조회 - 특정 멘토와 모임방에 동시에 속한 Mrp 찾기
    public List<Mrp> findByCompanyAndMeetingRoom(Company company, MeetingRoom meetingRoom) {
        return entityManager.createQuery(
                        "SELECT m FROM Mrp m WHERE m.mrpMtid = :company AND m.mrpMrid = :meetingRoom", Mrp.class)
                .setParameter("company", company)
                .setParameter("meetingRoom", meetingRoom)
                .getResultList();
    }
    // MrpRepository 클래스 내에 특정 모임방과 멘토에 속한 Mrp 찾기 메소드 추가
    public List<Mrp> findByMeetingRoomAndMentor(MeetingRoom meetingRoom) {
        return entityManager.createQuery(
                        "SELECT m FROM Mrp m WHERE m.mrpMrid = :meetingRoom AND m.mrpMtid IS NOT NULL", Mrp.class)
                .setParameter("meetingRoom", meetingRoom)
                .getResultList();
    }
    // 추가적인 조합 검색 메소드를 여기에 구현할 수 있습니다.
}
