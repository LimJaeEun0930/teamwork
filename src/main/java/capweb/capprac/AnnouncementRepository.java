package capweb.capprac;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Repository
public class AnnouncementRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 Announcement 저장
    @Transactional
    public void save(Announcement announcement) {
        entityManager.persist(announcement);
    }

    // Read - anmIndex로 Announcement 찾기
    public Announcement findAnnouncementByIndex(int anmIndex) {
        return entityManager.find(Announcement.class, anmIndex);
    }

    // Read - 모든 Announcement 찾기
    public List<Announcement> findAllAnnouncements() {
        TypedQuery<Announcement> query = entityManager.createQuery("SELECT a FROM Announcement a", Announcement.class);
        return query.getResultList();
    }

    // Update - Announcement 업데이트
    @Transactional
    public void update(Announcement announcement) {
        entityManager.merge(announcement);
    }

    // Delete - anmIndex로 Announcement 삭제
    @Transactional
    public void deleteByIndex(int anmIndex) {
        Announcement announcement = findAnnouncementByIndex(anmIndex);
        if (announcement != null) {
            entityManager.remove(announcement);
        }
    }

    // Read - anmName으로 Announcement 찾기
    public List<Announcement> findAnnouncementsByName(String anmName) {
        TypedQuery<Announcement> query = entityManager.createQuery(
                "SELECT a FROM Announcement a WHERE a.anmName = :anmName", Announcement.class);
        query.setParameter("anmName", anmName);
        return query.getResultList();
    }

    // Read - 기간으로 Announcement 찾기
    public List<Announcement> findAnnouncementsByPeriod(Date startDate, Date endDate) {
        TypedQuery<Announcement> query = entityManager.createQuery(
                "SELECT a FROM Announcement a WHERE a.anmStartDate <= :endDate AND a.anmEndDate >= :startDate", Announcement.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    // Read - anmEmptype으로 Announcement 찾기
    public List<Announcement> findAnnouncementsByEmptype(String anmEmptype) {
        TypedQuery<Announcement> query = entityManager.createQuery(
                "SELECT a FROM Announcement a WHERE a.anmEmptype = :anmEmptype", Announcement.class);
        query.setParameter("anmEmptype", anmEmptype);
        return query.getResultList();
    }

    // Read - anmRecruitm으로 Announcement 찾기
    public List<Announcement> findAnnouncementsByRecruitm(int anmRecruitm) {
        TypedQuery<Announcement> query = entityManager.createQuery(
                "SELECT a FROM Announcement a WHERE a.anmRecruitm = :anmRecruitm", Announcement.class);
        query.setParameter("anmRecruitm", anmRecruitm);
        return query.getResultList();
    }

    // Read - anmCpid로 Announcement 찾기
    public List<Announcement> findAnnouncementsByCompany(Company anmCpid) {
        TypedQuery<Announcement> query = entityManager.createQuery(
                "SELECT a FROM Announcement a WHERE a.anmCpid = :anmCpid", Announcement.class);
        query.setParameter("anmCpid", anmCpid);
        return query.getResultList();
    }

    public List<Announcement> findAnnouncementsByDateRangeAndCompany(Date startDate, Date endDate,Company anmCpid) {
        TypedQuery<Announcement> query = entityManager.createQuery(
                "SELECT a FROM Announcement a WHERE a.anmStartDate >= :startDate AND a.anmEndDate <= :endDate AND a.anmCpid = :anmCpid",
                Announcement.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("anmCpid", anmCpid);
        return query.getResultList();
    }

    // 추가적인 메소드들을 여기에 구현할 수 있습니다.
}
