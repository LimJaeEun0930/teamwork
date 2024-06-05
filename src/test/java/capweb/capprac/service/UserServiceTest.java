package capweb.capprac.service;


import capweb.capprac.entity.User;
import capweb.capprac.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        // 테스트 데이터 세팅
        User user = new User();
        user.setUsId("testUser");
        user.setUsPw("testPass");
        user.setUsName("testName");
        user.setUsJoindate(new Date());
        user.setUsJoinIP("127.0.0.1");
        userRepository.save(user);
    }

    @Test
    @Transactional
    public void testIsUserExists() {
        // given
        String usId = "testUser";

        // when
        boolean result = userService.isUserExists(usId);

        // then
        assertTrue(result);
    }

    @Test
    @Transactional
    public void testRegisterUser() {
        // given
        String usId = "newUser";
        String usPw = "password";
        String usName = "name";
        String usJoinIP = "127.0.0.1";

        // when
        userService.registerUser(usId, usPw, usName, usJoinIP);

        // then
        List<User> newUsers = userRepository.findUserById(usId);
        assertThat(newUsers.get(0)).isNotNull();
        assertThat(newUsers.get(0).getUsId()).isEqualTo(usId);
    }

    @Test
    @Transactional
    public void testUpdateUser() {
        // given
        userService.registerUser("usid","uspw","usname","usip");
        int usIndex = 1; // 가정: 테스트 데이터의 인덱스
        String newPw = "newPassword";
        String newName = "newName";
        String newFixIP = "127.0.0.2";


        // when
        userService.updateUser(usIndex, newPw, newName, newFixIP);

        // then
        User updatedUser = userRepository.findUserByIndex(usIndex);
        assertThat(updatedUser.getUsPw()).isEqualTo(newPw);
        assertThat(updatedUser.getUsName()).isEqualTo(newName);
        assertThat(updatedUser.getUsFixIP()).isEqualTo(newFixIP);
    }

    @Test
    @Transactional
    public void testGetAllUsers() {
        // when
        List<User> users = userService.getAllUsers();

        // then
        assertThat(users).isNotEmpty();
    }

    @Test
    @Transactional
    public void testLoginUser_Success() {
        // given
        String usId = "testUser";
        String usPw = "testPass";

        // when
        User loggedInUser = userService.loginUser(usId, usPw);

        // then
        assertNotNull(loggedInUser);
        assertEquals(usId, loggedInUser.getUsId());
    }

    @Test
    @Transactional
    public void testLoginUser_Failure() {
        // given
        String usId = "testUser";
        String wrongPw = "wrongPass";

        // when
        User loggedInUser = userService.loginUser(usId, wrongPw);

        // then
        assertNull(loggedInUser);
    }

    @Test
    @Transactional
    public void testDeleteUserById() {
        // given
        String usId = "testUser";

        // when
        userService.deleteUserById(usId);

        // then
        List<User> results = userRepository.findUserById(usId);
        assertTrue(results.isEmpty());
    }

    // 로그아웃 메소드는 세션 관리를 통해 처리되므로, 테스트 환경에서는 구현하지 않습니다.
}
