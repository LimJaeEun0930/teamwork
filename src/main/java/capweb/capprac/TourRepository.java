package capweb.capprac;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class TourRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 Tour 저장
    public void save(Tour tour) {
        entityManager.persist(tour);
    }

    // Read - tourIndex로 Tour 찾기
    public Tour findTourByIndex(int tourIndex) {
        return entityManager.find(Tour.class, tourIndex);
    }

    // Read - 모든 Tour 찾기
    public List<Tour> findAllTours() {
        TypedQuery<Tour> query = entityManager.createQuery("SELECT t FROM Tour t", Tour.class);
        return query.getResultList();
    }

    // Update - Tour 업데이트
    public void update(Tour tour) {
        entityManager.merge(tour);
    }

    // Delete - tourIndex로 Tour 삭제
    public void deleteByIndex(int tourIndex) {
        Tour tour = findTourByIndex(tourIndex);
        if (tour != null) {
            entityManager.remove(tour);
        }
    }

    // Read - tourDay로 Tour 찾기
    public List<Tour> findToursByDay(Date tourDay) {
        TypedQuery<Tour> query = entityManager.createQuery(
                "SELECT t FROM Tour t WHERE t.tourDay = :tourDay", Tour.class);
        query.setParameter("tourDay", tourDay);
        return query.getResultList();
    }

    // Read - tourName으로 Tour 찾기
    public List<Tour> findToursByName(String tourName) {
        TypedQuery<Tour> query = entityManager.createQuery(
                "SELECT t FROM Tour t WHERE t.tourName = :tourName", Tour.class);
        query.setParameter("tourName", tourName);
        return query.getResultList();
    }

    // Read - tourRecruitm으로 Tour 찾기
    public List<Tour> findToursByRecruitm(int tourRecruitm) {
        TypedQuery<Tour> query = entityManager.createQuery(
                "SELECT t FROM Tour t WHERE t.tourRecruitm = :tourRecruitm", Tour.class);
        query.setParameter("tourRecruitm", tourRecruitm);
        return query.getResultList();
    }

    // Read - tourCpid로 Tour 찾기
    public List<Tour> findToursByCompany(Company tourCpid) {
        TypedQuery<Tour> query = entityManager.createQuery(
                "SELECT t FROM Tour t WHERE t.tourCpid = :tourCpid", Tour.class);
        query.setParameter("tourCpid", tourCpid);
        return query.getResultList();
    }
    // Read - tourDay와 tourCpid로 Tour 찾기
    public List<Tour> findToursByDayAndCompany(Date tourDay, Company tourCpid) {
        TypedQuery<Tour> query = entityManager.createQuery(
                "SELECT t FROM Tour t WHERE t.tourDay = :tourDay AND t.tourCpid = :tourCpid", Tour.class);
        query.setParameter("tourDay", tourDay);
        query.setParameter("tourCpid", tourCpid);
        return query.getResultList();
    }

    // 추가적인 메소드들을 여기에 구현할 수 있습니다.
}
