package CapstoneDesign.Backendserver.util;

import CapstoneDesign.Backendserver.domain.dto.TestUserCreateFormData;
import CapstoneDesign.Backendserver.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class TestCreateUser {
    public User TestCreateUSer(TestUserCreateFormData testUserCreateFormData) {
        User user = new User();
        user.setId(testUserCreateFormData.getUsId());
        user.setPassword(testUserCreateFormData.getUsPw());
        user.setName(testUserCreateFormData.getUsName());
        return user;
    }
}
