package capweb.capprac.repository;

import capweb.capprac.entity.USer;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class USerRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private USerRepository userRepository;


    @Test
    @Transactional
    void whenSaveUser_thenUserIsSaved() {
        USer user = new USer();
        user.setUsId("testId");
        user.setUsPw("testPw");
        user.setUsName("testName");

        userRepository.save(user);

        USer foundUser = entityManager.find(USer.class, user.getUsIndex());
        assertNotNull(foundUser);
        assertEquals("testId", foundUser.getUsId());
    }
    @Test
    @Transactional
    void whenFindUserByIndex_thenCorrectUserIsReturned() {
        USer user = new USer();
        user.setUsId("testId");
        user.setUsPw("testPw");
        user.setUsName("testName");
        entityManager.persist(user);

        USer foundUser = userRepository.findUserByIndex(user.getUsIndex());
        assertNotNull(foundUser);
        assertEquals("testId", foundUser.getUsId());
    }

    @Test
    @Transactional
    void whenFindAllUsers_thenAllUsersAreReturned() {
        USer user1 = new USer();
        user1.setUsId("testId1");
        user1.setUsPw("testPw");
        user1.setUsName("testName");
        entityManager.persist(user1);

        USer user2 = new USer();
        user2.setUsId("testId2");
        user2.setUsPw("testPw");
        user2.setUsName("testName");
        entityManager.persist(user2);

        List<USer> users = userRepository.findAllUsers();
        assertNotNull(users);
        assertTrue(users.size() >= 2);
    }
    @Test
    @Transactional
    void whenUpdateUser_thenUserIsUpdated() {
        USer user = new USer();
        user.setUsId("testId");
        user.setUsPw("testPw");
        user.setUsName("testName");
        entityManager.persist(user);

        user.setUsPw("newPw");
        userRepository.update(user);

        USer updatedUser = entityManager.find(USer.class, user.getUsIndex());
        assertEquals("newPw", updatedUser.getUsPw());
    }
    @Test
    @Transactional
    void whenDeleteUserByIndex_thenUserIsDeleted() {
        USer user = new USer();
        user.setUsId("testId");
        user.setUsPw("testPw");
        user.setUsName("testName");
        entityManager.persist(user);

        userRepository.deleteByIndex(user.getUsIndex());

        USer deletedUser = entityManager.find(USer.class, user.getUsIndex());
        assertNull(deletedUser);
    }
    @Test
    @Transactional
    void whenFindUserByNonExistingId_thenExceptionIsThrown() {
        USer user = new USer();
        user.setUsId("testId");
        user.setUsPw("testPw");
        user.setUsName("testName");
        entityManager.persist(user);
        // 존재하지 않는 ID로 사용자를 찾을 때 예외가 발생하는지 검증
        List<USer> results = userRepository.findUserById("nonExistingId");
        assertTrue(results.isEmpty());
    }

    //위에거로 수정
//	@Test
//	@Transactional
//	void whenFindUserById_thenCorrectUserIsReturned() {
//		User user = new User();
//		user.setUsId("testId");
//		user.setUsPw("testPw");
//		user.setUsName("testName");
//		user.setUsJoindate(new Date());
//		user.setUsJoinIP("127.0.0.1");
//		entityManager.persist(user);
//
//		User foundUser = userRepository.findUserById("uniqueId");
//		assertNotNull(foundUser);
//		assertEquals("uniqueId", foundUser.getUsId());
//	}

    @Test
    @Transactional
    void whenFindUsersByName_thenCorrectUsersAreReturned() {
        USer user1 = new USer();
        user1.setUsId("testId");
        user1.setUsPw("testPw");
        user1.setUsName("testName");
        entityManager.persist(user1);

        USer user2 = new USer();
        user2.setUsId("testId2");
        user2.setUsPw("testPw");
        user2.setUsName("testName");
        entityManager.persist(user2);

        List<USer> users = userRepository.findUsersByName("testName");
        assertNotNull(users);
        assertTrue(users.size() >= 2);
    }
    /*------------------------------유저레포지토리 끝-------------------*/

    // 추가적인 테스트 케이스들을 여기에 구현할 수 있습니다.
}
