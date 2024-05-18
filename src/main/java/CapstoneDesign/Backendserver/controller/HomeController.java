package CapstoneDesign.Backendserver.controller;

import CapstoneDesign.Backendserver.SessionConst;
import CapstoneDesign.Backendserver.domain.User;
import CapstoneDesign.Backendserver.domain.UserLogin;
import CapstoneDesign.Backendserver.service.MailService;
import CapstoneDesign.Backendserver.service.UserService;
import CapstoneDesign.Backendserver.validator.LoginValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;


@Controller
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class HomeController {


    private final UserService userService;
    private final MailService mailService;
    private final LoginValidator loginValidator;

    @InitBinder("userLogin")//이렇게 지정해줌으로서 mainPage에서 쿼리이후
    // java.lang.IllegalStateException: Invalid target for Validator
    // 에러가 나는 것을 막아주었다. 그런데 왜 이런 에러가 나는지는 모르겠다. @Validated도 넣지 않았는데 검증을 왜하지?스프링문젠가?
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(loginValidator);
    }


    @GetMapping
    public String mainPage(Model model,HttpServletRequest request)
    {

        HttpSession session = request.getSession(false);
        if (session == null) {
            log.info("session is null");
            return "home";
        }
        //login
        //log.info("InitBinder에 userLogin안써주면 여기서 터짐.");
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        if (loginUser == null) {
            log.info("user in session is null");
            return "home";
        }
        model.addAttribute("user", loginUser);
        return "loginHome";
    }

    @GetMapping("login")
    public String loginPage(@ModelAttribute("userLogin") UserLogin userLogin)
    {
        return "login";
    }

    @PostMapping("login")
    public String doLogin(@Validated @ModelAttribute("userLogin") UserLogin userLogin,
                          BindingResult bindingResult, HttpServletRequest request)
    {
        log.info("login시도");

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "/login";
        }
        log.info("login완료");
        User loginUser = (User)userService.findUser(userLogin.getId());

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3600);
        session.setAttribute(SessionConst.LOGIN_USER,loginUser);
//        User loginUser = (User) userService.findUser(userLogin.getId());
//        Cookie idCookie = new Cookie("userId", String.valueOf(loginUser.getId()));
//        response.addCookie(idCookie);
//        log.info("cookie created. : {}", idCookie.getName());

        return "redirect:/";
    }

    @PostMapping("logout")
    public String logout(HttpServletRequest request)
    {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName)
    {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

    }

    @GetMapping("register")
    public String register_get(@ModelAttribute("user") User user, Model model)
    {

        log.info("회원가입창 입장");
        return "register";
    }

}
