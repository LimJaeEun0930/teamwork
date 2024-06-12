package capweb.capprac.repository;
//중요!!!!
//매개변수로 엔티티(User) usid 등 되어있는것들은 실제 User의 usid를 가져오는것이 아닌 User객체를 가져오는것
//User의 usid를 가져오려면 매개변수가 String usid가 되어야한다.
//외래키를 이용하려면 필드가 아닌 객체를 이용해야함
//필드를 이용할때는 매개변수로 String타입임
//중요!!
//capweb.capprac의 User를 인식 못할때 파일의 invalidate cashes and restart하면 잘 된다.

import capweb.capprac.entity.USer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class USerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 User 저장
    @Transactional
    public void save(USer user) {
        entityManager.persist(user);
    }

    // Read - usIndex로 User 찾기
    public USer findUserByIndex(int usIndex) {
        return entityManager.find(USer.class, usIndex);
    }

    // Read - 모든 User 찾기
    public List<USer> findAllUsers() {
        TypedQuery<USer> query = entityManager.createQuery("SELECT u FROM USer u", USer.class);
        return query.getResultList();
    }

    // Update - User 업데이트
    @Transactional
    public void update(USer user) {
        entityManager.merge(user);
    }

    // Delete - usIndex로 User 삭제
    @Transactional
    public void deleteByIndex(int usIndex) {
        USer user = findUserByIndex(usIndex);
        if (user != null) {
            entityManager.remove(user);
        }
    }
    // Read - usId로 User 찾기
    public List<USer> findUserById(String usId) {
        TypedQuery<USer> query = entityManager.createQuery(
                "SELECT u FROM USer u WHERE u.usId = :usId", USer.class);
        query.setParameter("usId", usId);
        return query.getResultList(); // 결과 리스트 반환
    }
    // Read - usName으로 User 찾기
    public List<USer> findUsersByName(String usName) {
        TypedQuery<USer> query = entityManager.createQuery(
                "SELECT u FROM USer u WHERE u.usName = :usName", USer.class);
        query.setParameter("usName", usName);
        return query.getResultList();
    }


    // 추가적인 메소드들을 여기에 구현할 수 있습니다.
}
