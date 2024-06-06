package capweb.capprac.repository;
//중요!!!!
//매개변수로 엔티티(User) usid 등 되어있는것들은 실제 User의 usid를 가져오는것이 아닌 User객체를 가져오는것
//User의 usid를 가져오려면 매개변수가 String usid가 되어야한다.
//외래키를 이용하려면 필드가 아닌 객체를 이용해야함
//필드를 이용할때는 매개변수로 String타입임
import CapstoneDesign.Backendserver.domain.User;
import capweb.capprac.entity.Company;
import capweb.capprac.entity.Plan;
import capweb.capprac.entity.USer;
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

    // Read - planUsid로 Plan 찾기----!!!조회할때 필요!!!
    public List<Plan> findPlansByUser(USer planUsid) {
        TypedQuery<Plan> query = entityManager.createQuery(
                "SELECT p FROM Plan p WHERE p.planUsid = :planUsid", Plan.class);
        query.setParameter("planUsid", planUsid);
        return query.getResultList();
    }

    // Read - planCpid로 Plan 찾기----!!!조회할때 필요!!!
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

    // Read - planId와 유저아이디로 Plan 찾기
    public List<Plan> findPlansByDateAndUser(Date planId, USer planUsid) {
        TypedQuery<Plan> query = entityManager.createQuery(
                "SELECT p FROM Plan p WHERE p.planId = :planId AND p.planUsid = :planUsid", Plan.class);
        query.setParameter("planId", planId);
        query.setParameter("planUsid", planUsid);
        return query.getResultList();
    }

    // Read - planId와 산업체아이디로 Plan 찾기
    public List<Plan> findPlansByDateAndCompany(Date planId, Company planCpid) {
        TypedQuery<Plan> query = entityManager.createQuery(
                "SELECT p FROM Plan p WHERE p.planId = :planId AND p.planCpid = :planCpid", Plan.class);
        query.setParameter("planId", planId);
        query.setParameter("planCpid", planCpid);
        return query.getResultList();
    }
    //Read - 아이디와 월을 입력받아 해당하는 Plan 찾기---!!!조회할때 필요!!!
    public List<Plan> findPlansByUserIdAndMonth(String userId, int month) {
        String jpql = "select p from Plan p where function('MONTH', p.planId) = :month and " +
                "(p.planUsid.usId = :userId or p.planCpid.cpId = :userId)";
        TypedQuery<Plan> query = entityManager.createQuery(jpql, Plan.class);
        query.setParameter("userId", userId);
        query.setParameter("month", month);
        return query.getResultList();
    }

    // 추가적인 메소드들을 여기에 구현할 수 있습니다.
}
