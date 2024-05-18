package CapstoneDesign.Backendserver.validator;

import CapstoneDesign.Backendserver.domain.User;
import CapstoneDesign.Backendserver.domain.UserLogin;
import CapstoneDesign.Backendserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class LoginValidator implements Validator {
    private final UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return UserLogin.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserLogin userLogin = (UserLogin) target;
        Object findUser = userService.findUser(userLogin.getId());//이렇게 함으로써 query를 한번만 날릴 수 있다.
        if(findUser.equals("ID_NOT_FOUND")){
            errors.rejectValue("id","required");
            return;
        }
        if(
           !((User)findUser).getPassword().
                   equals(userLogin.getPw())){
            errors.rejectValue("pw","required");
        }

    }
}
