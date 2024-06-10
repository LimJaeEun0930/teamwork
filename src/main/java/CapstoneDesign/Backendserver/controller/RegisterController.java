package CapstoneDesign.Backendserver.controller;

import CapstoneDesign.Backendserver.domain.Company;
import CapstoneDesign.Backendserver.domain.User;
import CapstoneDesign.Backendserver.service.CompanyService;
import CapstoneDesign.Backendserver.service.MailService;
import CapstoneDesign.Backendserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;
    private final MailService mailService;
    private final CompanyService companyService;

    @GetMapping("entrance/company")
    public String register_company_entrance(@ModelAttribute("company") Company company, Model model) {

        return "register/register_company";
    }

    @PostMapping("cp")
    public String register_company_post(@ModelAttribute("company") Company company, Model model) {
        companyService.registerCompany(company);
        return "home";
    }
    @GetMapping("entrance/normal")
    public String register_normal_user_entrance(@ModelAttribute("user") User user, Model model) {

        return "register/register_normalUser";
    }

    @GetMapping("entrance/mentor")
    public String register_mentor_user_entrance(@ModelAttribute("user") User user, Model model) {
        return "register/register_mentor";
    }
    @PostMapping
    public String register_post(@Validated @ModelAttribute User user, BindingResult bindingResult)
    {
        log.info("{}", user.getName());
        log.info("{}", user.getBirth());
        if (bindingResult.hasErrors()) {
            return "register/register_normalUser";
        }
        userService.join(user);

        return "login/login";
    }

    @GetMapping("idDuplicateCheck")
    @ResponseBody
    public ResponseEntity<String> register_idDuplicateCheck(@RequestParam String id)
    {
        log.info("id중복확인 실행됨.");
        boolean duplicated = userService.validateDuplicateMember(id);
        log.info("duplicated:{}", duplicated);
        if (duplicated) {
            return new ResponseEntity<>("duplicated", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }
    }

    @GetMapping("sendingAuthenticationMail")
    @ResponseBody
    public ResponseEntity<String> register_sendingAuthenticationMail
            (@RequestParam String email, Model model)
    {
        log.info("이메일 인증 실행...{}", email);
        mailService.sendingAuthenticationMail(email, model);

        return new ResponseEntity<String>((String) model.getAttribute("randomNumber"),HttpStatus.OK);
    }

     @GetMapping("cpIdDuplicateCheck")
    @ResponseBody
    public ResponseEntity<String> cp_register_idDuplicateCheck(@RequestParam String cpid)
    {
        log.info("cpid중복확인 실행됨.");
        boolean duplicated = companyService.validateDuplicateCompany(cpid);
        log.info("duplicated:{}", duplicated);
        if (duplicated) {
            return new ResponseEntity<>("duplicated", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }
    }
//
//    @GetMapping("cp/sendingAuthenticationMail")
//    @ResponseBody
//    public ResponseEntity<String> cp_register_sendingAuthenticationMail
//            (@RequestParam String email, Model model)
//    {
//        log.info("이메일 인증 실행...{}", email);
//        mailService.sendingAuthenticationMail(email, model);
//
//        return new ResponseEntity<String>((String) model.getAttribute("randomNumber"),HttpStatus.OK);
//    }
//}
}
