package capweb.capprac.repository;
//중요!!!!
//매개변수로 엔티티(User) usid 등 되어있는것들은 실제 User의 usid를 가져오는것이 아닌 User객체를 가져오는것
//User의 usid를 가져오려면 매개변수가 String usid가 되어야한다.
//외래키를 이용하려면 필드가 아닌 객체를 이용해야함
//필드를 이용할때는 매개변수로 String타입임
import capweb.capprac.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 User 저장
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    // Read - usIndex로 User 찾기
    public User findUserByIndex(int usIndex) {
        return entityManager.find(User.class, usIndex);
    }

    // Read - 모든 User 찾기
    public List<User> findAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    // Update - User 업데이트
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    // Delete - usIndex로 User 삭제
    @Transactional
    public void deleteByIndex(int usIndex) {
        User user = findUserByIndex(usIndex);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    // Read - usId로 User 찾기
    public List<User> findUserById(String usId) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.usId = :usId", User.class);
        query.setParameter("usId", usId);
        return query.getResultList(); // 결과 리스트 반환
    }



    // Read - usName으로 User 찾기
    public List<User> findUsersByName(String usName) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.usName = :usName", User.class);
        query.setParameter("usName", usName);
        return query.getResultList();
    }
    // Read - usjoindate으로 User 찾기
    public List<User> findUsersByJoindate(Date usJoindate) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.usJoindate = :usJoindate", User.class);
        query.setParameter("usJoindate", usJoindate);
        return query.getResultList();
    }

    // 추가적인 메소드들을 여기에 구현할 수 있습니다.
}
