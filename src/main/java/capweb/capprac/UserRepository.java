package capweb.capprac;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 User 저장
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
    public void update(User user) {
        entityManager.merge(user);
    }

    // Delete - usIndex로 User 삭제
    public void deleteByIndex(int usIndex) {
        User user = findUserByIndex(usIndex);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    // Read - usId로 User 찾기
    public User findUserById(String usId) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.usId = :usId", User.class);
        query.setParameter("usId", usId);
        return query.getSingleResult();
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
