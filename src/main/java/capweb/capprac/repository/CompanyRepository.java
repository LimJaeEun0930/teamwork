package capweb.capprac.repository;
//중요!!!!
//매개변수로 엔티티(User) usid 등 되어있는것들은 실제 User의 usid를 가져오는것이 아닌 User객체를 가져오는것
//User의 usid를 가져오려면 매개변수가 String usid가 되어야한다.
//외래키를 이용하려면 필드가 아닌 객체를 이용해야함
//필드를 이용할때는 매개변수로 String타입임
import capweb.capprac.entity.Company;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Repository
public class CompanyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 Company 저장
    @Transactional
    public void save(Company company) {
        entityManager.persist(company);
    }

    // Read - cpIndex로 Company 찾기
    public Company findCompanyByIndex(int cpIndex) {
        return entityManager.find(Company.class, cpIndex);
    }

    // Read - 모든 Company 찾기
    public List<Company> findAllCompanies() {
        TypedQuery<Company> query = entityManager.createQuery("SELECT c FROM Company c", Company.class);
        return query.getResultList();
    }

    // Update - Company 업데이트
    @Transactional
    public void update(Company company) {
        entityManager.merge(company);
    }

    // Delete - cpIndex로 Company 삭제
    @Transactional
    public void deleteByIndex(int cpIndex) {
        Company company = findCompanyByIndex(cpIndex);
        if (company != null) {
            entityManager.remove(company);
        }
    }

    // Read - cpId로 단일 Company 찾기
    public List<Company> findCompanyById(String cpId) {
        TypedQuery<Company> query = entityManager.createQuery(
                "SELECT c FROM Company c WHERE c.cpId = :cpId", Company.class);
        query.setParameter("cpId", cpId);
        return query.getResultList();
    }

    // Read - cpName으로 단일 Company 찾기
    public List<Company> findCompanyByName(String cpName) {
        TypedQuery<Company> query = entityManager.createQuery(
                "SELECT c FROM Company c WHERE c.cpName = :cpName", Company.class);
        query.setParameter("cpName", cpName);
        return query. getResultList();
    }

    // Read - cpAddr로 단일 Company 찾기
    public List<Company> findCompanyByAddr(String cpAddr) {
        TypedQuery<Company> query = entityManager.createQuery(
                "SELECT c FROM Company c WHERE c.cpAddr = :cpAddr", Company.class);
        query.setParameter("cpAddr", cpAddr);
        return query. getResultList();
    }

    // Read - cpCategory로 Company 찾기
    public List<Company> findCompaniesByCategory(String cpCategory) {
        TypedQuery<Company> query = entityManager.createQuery(
                "SELECT c FROM Company c WHERE c.cpCategory = :cpCategory", Company.class);
        query.setParameter("cpCategory", cpCategory);
        return query.getResultList();
    }

    // Read - cpMtid로 단일 Company 찾기
    public List<Company> findCompanyByMtid(String cpMtid) {
        TypedQuery<Company> query = entityManager.createQuery(
                "SELECT c FROM Company c WHERE c.cpMtid = :cpMtid", Company.class);
        query.setParameter("cpMtid", cpMtid);
        return query. getResultList();
    }

    // Read - cpMtname으로 Company 찾기
    public List<Company> findCompaniesByMtname(String cpMtname) {
        TypedQuery<Company> query = entityManager.createQuery(
                "SELECT c FROM Company c WHERE c.cpMtname = :cpMtname", Company.class);
        query.setParameter("cpMtname", cpMtname);
        return query.getResultList();
    }
    // Read - cpFixdate로 Company 찾기
    public List<Company> findCompaniesByFixdate(Date cpFixdate) {
        TypedQuery<Company> query = entityManager.createQuery(
                "SELECT c FROM Company c WHERE c.cpFixdate = :cpFixdate", Company.class);
        query.setParameter("cpFixdate", cpFixdate);
        return query.getResultList();
    }

    // Read - cpFixIP로 Company 찾기
    public List<Company> findCompaniesByFixIP(String cpFixIP) {
        TypedQuery<Company> query = entityManager.createQuery(
                "SELECT c FROM Company c WHERE c.cpFixIP = :cpFixIP", Company.class);
        query.setParameter("cpFixIP", cpFixIP);
        return query.getResultList();
    }

    // Read - cpJoindate로 Company 찾기
    public List<Company> findCompaniesByJoindate(Date cpJoindate) {
        TypedQuery<Company> query = entityManager.createQuery(
                "SELECT c FROM Company c WHERE c.cpJoindate = :cpJoindate", Company.class);
        query.setParameter("cpJoindate", cpJoindate);
        return query.getResultList();
    }

    // Read - cpJoinIP로 Company 찾기
    public List<Company> findCompaniesByJoinIP(String cpJoinIP) {
        TypedQuery<Company> query = entityManager.createQuery(
                "SELECT c FROM Company c WHERE c.cpJoinIP = :cpJoinIP", Company.class);
        query.setParameter("cpJoinIP", cpJoinIP);
        return query.getResultList();
    }

    // Read - 주소에 특정 문자열을 포함하는 Company 찾기
    public List<Company> findCompaniesByAddressContaining(String address) {
        TypedQuery<Company> query = entityManager.createQuery(
                "SELECT c FROM Company c WHERE c.cpAddr LIKE :address", Company.class);
        query.setParameter("address", "%" + address + "%");
        return query.getResultList();
    }

    // 추가적인 메소드들..

    // 추가적인 조합 검색 메소드들...
    // 추가적인 메소드들을 여기에 구현할 수 있습니다.
}
