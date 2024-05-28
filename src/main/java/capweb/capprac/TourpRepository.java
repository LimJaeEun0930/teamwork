package capweb.capprac;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class TourpRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 Tourp 저장
    public void save(Tourp tourp) {
        entityManager.persist(tourp);
    }

    // Read - tourpIndex로 Tourp 찾기
    public Tourp findTourpByIndex(int tourpIndex) {
        return entityManager.find(Tourp.class, tourpIndex);
    }

    // Read - 모든 Tourp 찾기
    public List<Tourp> findAllTourps() {
        TypedQuery<Tourp> query = entityManager.createQuery("SELECT t FROM Tourp t", Tourp.class);
        return query.getResultList();
    }

    // Update - Tourp 업데이트
    public void update(Tourp tourp) {
        entityManager.merge(tourp);
    }

    // Delete - tourpIndex로 Tourp 삭제
    public void deleteByIndex(int tourpIndex) {
        Tourp tourp = findTourpByIndex(tourpIndex);
        if (tourp != null) {
            entityManager.remove(tourp);
        }
    }

    // Read - tourpUsid로 Tourp 찾기
    public List<Tourp> findTourpsByUser(User tourpUsid) {
        TypedQuery<Tourp> query = entityManager.createQuery(
                "SELECT t FROM Tourp t WHERE t.tourpUsid = :tourpUsid", Tourp.class);
        query.setParameter("tourpUsid", tourpUsid);
        return query.getResultList();
    }

    // Read - tourpTourid로 Tourp 찾기
    public List<Tourp> findTourpsByTour(Tour tourpTourid) {
        TypedQuery<Tourp> query = entityManager.createQuery(
                "SELECT t FROM Tourp t WHERE t.tourpTourid = :tourpTourid", Tourp.class);
        query.setParameter("tourpTourid", tourpTourid);
        return query.getResultList();
    }

    // Read - tourpUsid와 tourpTourid 조합으로 Tourp 찾기
    public List<Tourp> findTourpsByUserAndTour(User tourpUsid, Tour tourpTourid) {
        TypedQuery<Tourp> query = entityManager.createQuery(
                "SELECT t FROM Tourp t WHERE t.tourpUsid = :tourpUsid AND t.tourpTourid = :tourpTourid", Tourp.class);
        query.setParameter("tourpUsid", tourpUsid);
        query.setParameter("tourpTourid", tourpTourid);
        return query.getResultList();
    }

    // 추가적인 메소드들을 여기에 구현할 수 있습니다.
}
