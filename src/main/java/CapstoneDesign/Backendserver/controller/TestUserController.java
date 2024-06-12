package CapstoneDesign.Backendserver.controller;

import CapstoneDesign.Backendserver.domain.User;
import CapstoneDesign.Backendserver.domain.dto.TestUserCreateFormData;
import CapstoneDesign.Backendserver.repository.UserRepository;
import CapstoneDesign.Backendserver.service.UserService;
import CapstoneDesign.Backendserver.util.TestCreateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class TestUserController {
    @Autowired
    private TestCreateUser testcreateUSer;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userPage() {
        return "/board/userRegisterForm";
    }

    //0609 회원가입
    @PostMapping("/register")
    public String showRegistrationForm(@ModelAttribute TestUserCreateFormData testuSerCreateFormData) {
        User user = testcreateUSer.TestCreateUSer(testuSerCreateFormData);
        userService.join(user);
        return "redirect:/user";
    }
//
//    @GetMapping("/profile")
//    public String showUpdateForm(@RequestParam int usIndex, Model model) {
//
//        return "update";
//    }
//
//    @PostMapping("/update")
//    public String updateUser(@ModelAttribute USer user) {
//        userService.updateUser(user);
//        return "redirect:/user/profile";
//    }
//
//    @GetMapping("/delete")
//    public String deleteUser(@RequestParam String usId) {
//        userService.deleteUserById(usId);
//        return "redirect:/user/list";
//    }
//
//    @GetMapping("/login")
//    public String showLoginForm() {
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String loginUser(@RequestParam String usId, @RequestParam String usPw, Model model) {
//        USer user = userService.loginUser(usId, usPw);
//        if (user != null) {
//            model.addAttribute("user", user);
//            return "redirect:/user/profile";
//        } else {
//            return "redirect:/user/login?error=true";
//        }
//    }
//
//    @GetMapping("/logout")
//    public String logoutUser(@RequestParam int usIndex) {
//        USer user = userRepository.findUserByIndex(usIndex);
//        userService.logoutUser(user);
//        return "redirect:/";
//    }
//
//    @GetMapping("/list")
//    public String listUsers(Model model) {
//        List<USer> users = userService.getAllUsers();
//        model.addAttribute("users", users);
//        return "userList";
//    }

    // 나머지 컨트롤러 메소드들...
}
