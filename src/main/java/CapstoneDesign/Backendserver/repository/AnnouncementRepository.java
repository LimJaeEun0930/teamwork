package CapstoneDesign.Backendserver.repository;

//중요!!!!
//매개변수로 엔티티(User) usid 등 되어있는것들은 실제 User의 usid를 가져오는것이 아닌 User객체를 가져오는것
//User의 usid를 가져오려면 매개변수가 String usid가 되어야한다.
//외래키를 이용하려면 필드가 아닌 객체를 이용해야함
//필드를 이용할때는 매개변수로 String타입임

import CapstoneDesign.Backendserver.domain.Announcement;
import CapstoneDesign.Backendserver.domain.Board;
import CapstoneDesign.Backendserver.domain.Company;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnnouncementRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 Announcement 저장

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

    public void update(Announcement announcement) {
        entityManager.merge(announcement);
    }

    // Delete - anmIndex로 Announcement 삭제

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

    //Read - 기간과 공지회사아이디로 공지찾기
    public List<Announcement> findAnnouncementsByDateRangeAndCompany(Date startDate, Date endDate,Company anmCpid) {
        TypedQuery<Announcement> query = entityManager.createQuery(
                "SELECT a FROM Announcement a WHERE a.anmStartDate >= :startDate AND a.anmEndDate <= :endDate AND a.anmCpid = :anmCpid",
                Announcement.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("anmCpid", anmCpid);
        return query.getResultList();
    }


    public Page<Announcement> findAllAnnouncements_paging(Pageable pageable) {
         int page = pageable.getPageNumber();
        int pageLimit = pageable.getPageSize();
        Sort sort = pageable.getSort();

        String sortBy = "id";
        if (sort != null && sort.isSorted()) {
            sortBy = sort.stream().findFirst().get().getProperty();
        }

        TypedQuery<Long> countQuery = entityManager.createQuery(
                "SELECT COUNT(b) FROM Announcement b", Long.class);

        long total = countQuery.getSingleResult();

        TypedQuery<Announcement> query = entityManager.createQuery(
                "SELECT b FROM Announcement b ORDER BY b." + sortBy + " DESC", Announcement.class);
        query.setFirstResult(page * pageLimit);
        query.setMaxResults(pageLimit);

        List<Announcement> content = query.getResultList();

        return new PageImpl<>(content, pageable, total);
    }


    // 추가적인 메소드들을 여기에 구현할 수 있습니다.
}
