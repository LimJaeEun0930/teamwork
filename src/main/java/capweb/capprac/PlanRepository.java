package capweb.capprac;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Repository
public class PlanRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 Plan 저장
    @Transactional
    public void save(Plan plan) {
        entityManager.persist(plan);
    }

    // Read - planIndex로 Plan 찾기
    public Plan findPlanByIndex(int planIndex) {
        return entityManager.find(Plan.class, planIndex);
    }

    // Read - 모든 Plan 찾기
    public List<Plan> findAllPlans() {
        TypedQuery<Plan> query = entityManager.createQuery("SELECT p FROM Plan p", Plan.class);
        return query.getResultList();
    }

    // Update - Plan 업데이트
    @Transactional
    public void update(Plan plan) {
        entityManager.merge(plan);
    }

    // Delete - planIndex로 Plan 삭제
    @Transactional
    public void deleteByIndex(int planIndex) {
        Plan plan = findPlanByIndex(planIndex);
        if (plan != null) {
            entityManager.remove(plan);
        }
    }

    // Read - planName으로 Plan 찾기
    public List<Plan> findPlansByName(String planName) {
        TypedQuery<Plan> query = entityManager.createQuery(
                "SELECT p FROM Plan p WHERE p.planName = :planName", Plan.class);
        query.setParameter("planName", planName);
        return query.getResultList();
    }

    // Read - planUsid로 Plan 찾기
    public List<Plan> findPlansByUser(User planUsid) {
        TypedQuery<Plan> query = entityManager.createQuery(
                "SELECT p FROM Plan p WHERE p.planUsid = :planUsid", Plan.class);
        query.setParameter("planUsid", planUsid);
        return query.getResultList();
    }

    // Read - planCpid로 Plan 찾기
    public List<Plan> findPlansByCompany(Company planCpid) {
        TypedQuery<Plan> query = entityManager.createQuery(
                "SELECT p FROM Plan p WHERE p.planCpid = :planCpid", Plan.class);
        query.setParameter("planCpid", planCpid);
        return query.getResultList();
    }

    // Read - planId(날짜)로 Plan 찾기
    public List<Plan> findPlansByDate(Date planId) {
        TypedQuery<Plan> query = entityManager.createQuery(
                "SELECT p FROM Plan p WHERE p.planId = :planId", Plan.class);
        query.setParameter("planId", planId);
        return query.getResultList();
    }

    // Read - planOpt로 Plan 찾기
    public List<Plan> findPlansByOption(int planOpt) {
        TypedQuery<Plan> query = entityManager.createQuery(
                "SELECT p FROM Plan p WHERE p.planOpt = :planOpt", Plan.class);
        query.setParameter("planOpt", planOpt);
        return query.getResultList();
    }

    // planId와 유저아이디로 Plan 찾기
    public List<Plan> findPlansByDateAndUser(Date planId, User planUsid) {
        TypedQuery<Plan> query = entityManager.createQuery(
                "SELECT p FROM Plan p WHERE p.planId = :planId AND p.planUsid = :planUsid", Plan.class);
        query.setParameter("planId", planId);
        query.setParameter("planUsid", planUsid);
        return query.getResultList();
    }

    // planId와 산업체아이디로 Plan 찾기
    public List<Plan> findPlansByDateAndCompany(Date planId, Company planCpid) {
        TypedQuery<Plan> query = entityManager.createQuery(
                "SELECT p FROM Plan p WHERE p.planId = :planId AND p.planCpid = :planCpid", Plan.class);
        query.setParameter("planId", planId);
        query.setParameter("planCpid", planCpid);
        return query.getResultList();
    }

    // 추가적인 메소드들을 여기에 구현할 수 있습니다.
}
