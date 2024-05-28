package capweb.capprac;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class AnmpRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 Anmp 저장
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
    public void update(Anmp anmp) {
        entityManager.merge(anmp);
    }

    // Delete - anmpIndex로 Anmp 삭제
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

    // 추가적인 메소드들을 여기에 구현할 수 있습니다.
}
