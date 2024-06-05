package capweb.capprac.repository;
//중요!!!!
//매개변수로 엔티티(User) usid 등 되어있는것들은 실제 User의 usid를 가져오는것이 아닌 User객체를 가져오는것
//User의 usid를 가져오려면 매개변수가 String usid가 되어야한다.
//외래키를 이용하려면 필드가 아닌 객체를 이용해야함
//필드를 이용할때는 매개변수로 String타입임
import capweb.capprac.entity.Company;
import capweb.capprac.entity.Tour;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Repository
public class TourRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 Tour 저장
    @Transactional
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
    @Transactional
    public void update(Tour tour) {
        entityManager.merge(tour);
    }

    // Delete - tourIndex로 Tour 삭제
    @Transactional
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
