package capweb.capprac.controller;

import capweb.capprac.dto.USerCreateFormData;
import capweb.capprac.entity.USer;
import capweb.capprac.repository.USerRepository;
import capweb.capprac.service.USerService;
import capweb.capprac.util.CreateUSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class USerController {

    @Autowired
    private CreateUSer createUSer;
    @Autowired
    private USerService userService;
    @Autowired
    private USerRepository userRepository;

    @GetMapping
    public String userPage() {
        return "/board/userRegisterForm";
    }

    //0609 회원가입
    @PostMapping("/register")
    public String showRegistrationForm(@ModelAttribute USerCreateFormData uSerCreateFormData) {
        USer uSer = createUSer.CreateUSer(uSerCreateFormData);
        userService.registerUser(uSer);
        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String showUpdateForm(@RequestParam int usIndex, Model model) {

        return "update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute USer user) {
        userService.updateUser(user);
        return "redirect:/user/profile";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam String usId) {
        userService.deleteUserById(usId);
        return "redirect:/user/list";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String usId, @RequestParam String usPw, Model model) {
        USer user = userService.loginUser(usId, usPw);
        if (user != null) {
            model.addAttribute("user", user);
            return "redirect:/user/profile";
        } else {
            return "redirect:/user/login?error=true";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(@RequestParam int usIndex) {
        USer user = userRepository.findUserByIndex(usIndex);
        userService.logoutUser(user);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        List<USer> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userList";
    }

    // 나머지 컨트롤러 메소드들...
}
