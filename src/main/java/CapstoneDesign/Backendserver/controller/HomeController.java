package CapstoneDesign.Backendserver.controller;

import CapstoneDesign.Backendserver.SessionConst;
import CapstoneDesign.Backendserver.domain.*;
import CapstoneDesign.Backendserver.service.BoardService;
import CapstoneDesign.Backendserver.service.CompanyService;
import CapstoneDesign.Backendserver.service.MailService;
import CapstoneDesign.Backendserver.service.UserService;
import CapstoneDesign.Backendserver.validator.LoginValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;
    private final UserService userService;
    private final MailService mailService;
    private final LoginValidator loginValidator;
    private final CompanyService companyService;

    @InitBinder("userLogin")//이렇게 지정해줌으로서 mainPage에서 쿼리이후
    // java.lang.IllegalStateException: Invalid target for Validator
    // 에러가 나는 것을 막아주었다. 그런데 왜 이런 에러가 나는지는 모르겠다. @Validated도 넣지 않았는데 검증을 왜하지?스프링문젠가?
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(loginValidator);
    }

    @ModelAttribute("jobCategory")
    public JobCategory[] JobCategories() {
        return JobCategory.values();
    }

    @GetMapping
    public String mainPage(Model model, HttpServletRequest request, @PageableDefault(page = 1) Pageable pageable)
    {

        HttpSession session = request.getSession(false);
        if (session == null) {
            log.info("session is null");
            return "home";
        }
        //login
        //log.info("InitBinder에 userLogin안써주면 여기서 터짐.");
        if (session.getAttribute(SessionConst.LOGIN_USER) instanceof User){
            User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
            model.addAttribute("user", loginUser);
        } else {
            Company loginCompany = (Company) session.getAttribute(SessionConst.LOGIN_USER);
            model.addAttribute("company", loginCompany);
        }


        Page<Board> boardList = boardService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("selectedCategory", null);

        return "home";
//        if (loginUser == null) {
//            log.info("user in session is null");
//            return "home";
//        }
//        model.addAttribute("user", loginUser);
//        return "loginHome";
    }

    @GetMapping("login/entrance")
    public String login_Entrance() {
        return "login/login_entrance";
    }
    @GetMapping("login/user")
    public String loginPage(@ModelAttribute("userLogin") UserLogin userLogin)
    {
        return "login/login";
    }

    @GetMapping("login/company")
    public String cpLoginPage(@ModelAttribute("companyLogin")CompanyLogin companyLogin) {
        return "login/login_cp";
    }

    @PostMapping("login/company")
    public String doLogin_cp(@Validated @ModelAttribute("companyLogin") CompanyLogin companyLogin,
                             BindingResult bindingResult, HttpServletRequest request) {

        log.info("login시도");

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "login/login_cp";
        }
        log.info("login완료");
        Company loginCompany;
        if(companyService.findCompany(companyLogin.getCpId()) instanceof Company) {
            loginCompany = (Company) companyService.findCompany(companyLogin.getCpId());
        } else{
            loginCompany  = null;
            return "login/login_cp";
        }


        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3600);
        session.setAttribute(SessionConst.LOGIN_USER, loginCompany); //LOGIN_COMPANY가 맞겠지만..
//        User loginUser = (User) userService.findUser(userLogin.getId());
//        Cookie idCookie = new Cookie("userId", String.valueOf(loginUser.getId()));
//        response.addCookie(idCookie);
//        log.info("cookie created. : {}", idCookie.getName());

        return "redirect:/";
    }
    @PostMapping("login/user")
    public String doLogin(@Validated @ModelAttribute("userLogin") UserLogin userLogin,
                          BindingResult bindingResult, HttpServletRequest request) {

        log.info("login시도");

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "login/login";
        }
        log.info("login완료");
        User loginUser = (User) userService.findUser(userLogin.getId());

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3600);
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
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
        return "register/register_entrance";
    }

//    @GetMapping("chat")
//    public String chat_get() {
//        return "chat";
//    }

    @GetMapping("calender/myCalender")
    public String myCalender() {
        return "HY/my_calender";
    }

    @GetMapping("calender/jobCalender")
    public String jobCalender() {
        return "HY/JobApplication_Calender";
    }

    @GetMapping("PlanHtml")
    public String myPlan() {
        return "PlanHtml";
    }
}