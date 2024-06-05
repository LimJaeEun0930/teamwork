package capweb.capprac.repository;
//중요!!!!
//매개변수로 엔티티(User) usid 등 되어있는것들은 실제 User의 usid를 가져오는것이 아닌 User객체를 가져오는것
//User의 usid를 가져오려면 매개변수가 String usid가 되어야한다.
//외래키를 이용하려면 필드가 아닌 객체를 이용해야함
//필드를 이용할때는 매개변수로 String타입임
import capweb.capprac.entity.Tour;
import capweb.capprac.entity.Tourp;
import capweb.capprac.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TourpRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 Tourp 저장
    @Transactional
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
    @Transactional
    public void update(Tourp tourp) {
        entityManager.merge(tourp);
    }

    // Delete - tourpIndex로 Tourp 삭제
    @Transactional
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
