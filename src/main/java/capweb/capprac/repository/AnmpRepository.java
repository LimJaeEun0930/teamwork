package capweb.capprac.repository;
//중요!!!!
//매개변수로 엔티티(User) usid 등 되어있는것들은 실제 User의 usid를 가져오는것이 아닌 User객체를 가져오는것
//User의 usid를 가져오려면 매개변수가 String usid가 되어야한다.
//외래키를 이용하려면 필드가 아닌 객체를 이용해야함
//필드를 이용할때는 매개변수로 String타입임
import capweb.capprac.entity.Anmp;
import capweb.capprac.entity.Announcement;
import capweb.capprac.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Repository
public class AnmpRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 Anmp 저장
    @Transactional
    public void save(Anmp anmp) {
        entityManager.persist(anmp);
    }

    // Read - anmpIndex로 Anmp 찾기
    public Anmp findAnmpByIndex(int anmpIndex) {
        return entityManager.find(Anmp.class, anmpIndex);
    }

    // Read - 모든 Anmp 찾기
    public List<Anmp> findAllAnmps() {
        TypedQuery<Anmp> query = entityManager.createQuery("SELECT a FROM Anmp a", Anmp.class);
        return query.getResultList();
    }

    // Update - Anmp 업데이트
    @Transactional
    public void update(Anmp anmp) {
        entityManager.merge(anmp);
    }

    // Delete - anmpIndex로 Anmp 삭제
    @Transactional
    public void deleteByIndex(int anmpIndex) {
        Anmp anmp = findAnmpByIndex(anmpIndex);
        if (anmp != null) {
            entityManager.remove(anmp);
        }
    }
    // Read - anmpUsid로 Anmp 찾기
    public List<Anmp> findAnmpsByUser(User anmpUsid) {
        TypedQuery<Anmp> query = entityManager.createQuery(
                "SELECT a FROM Anmp a WHERE a.anmpUsid = :anmpUsid", Anmp.class);
        query.setParameter("anmpUsid", anmpUsid);
        return query.getResultList();
    }

    // Read - anmpAnmid로 Anmp 찾기
    public List<Anmp> findAnmpsByAnnouncement(Announcement anmpAnmid) {
        TypedQuery<Anmp> query = entityManager.createQuery(
                "SELECT a FROM Anmp a WHERE a.anmpAnmid = :anmpAnmid", Anmp.class);
        query.setParameter("anmpAnmid", anmpAnmid);
        return query.getResultList();
    }

    // Read - anmpUsid와 anmpAnmid 조합으로 Anmp 찾기
    public List<Anmp> findAnmpsByUserAndAnnouncement(User anmpUsid, Announcement anmpAnmid) {
        TypedQuery<Anmp> query = entityManager.createQuery(
                "SELECT a FROM Anmp a WHERE a.anmpUsid = :anmpUsid AND a.anmpAnmid = :anmpAnmid", Anmp.class);
        query.setParameter("anmpUsid", anmpUsid);
        query.setParameter("anmpAnmid", anmpAnmid);
        return query.getResultList();
    }
    //Read - 사용자아이디와 시작날짜와 종료날짜를 입력받아 참여한 공지 보기
    public List<Anmp> findAnmpsByUserAndDateRange(String userId, Date startDate, Date endDate) {
        String jpql = "SELECT a FROM Anmp a WHERE a.anmpUsid.usId = :userId AND a.anmpAnmid.anmStartDate >= :startDate AND a.anmpAnmid.anmEndDate <= :endDate";
        TypedQuery<Anmp> query = entityManager.createQuery(jpql, Anmp.class);
        query.setParameter("userId", userId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
    //Read - 사용자아이디와 월을 입력받아 해당하는 참여하는 공지 찾기
    public List<Anmp> findAnnouncementsByUserIdAndMonth(String userId, int month) {
        String jpql = "select a from Anmp a where function('MONTH', a.anmpAnmid.anmStartDate) = :month " +
                "and a.anmpUsid.usId = :userId";
        TypedQuery<Anmp> query = entityManager.createQuery(jpql, Anmp.class);
        query.setParameter("userId", userId);
        query.setParameter("month", month);
        return query.getResultList();
    }

    // 추가적인 메소드들을 여기에 구현할 수 있습니다.
}
