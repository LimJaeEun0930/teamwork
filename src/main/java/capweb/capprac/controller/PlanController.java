package capweb.capprac.controller;

import CapstoneDesign.Backendserver.repository.UserRepository;
import capweb.capprac.entity.Company;
import capweb.capprac.entity.Plan;
import capweb.capprac.entity.USer;
import capweb.capprac.repository.CompanyRepository;
import capweb.capprac.repository.PlanRepository;
import capweb.capprac.repository.USerRepository;
import capweb.capprac.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    private PlanService planService;
    @Autowired
    private USerRepository userRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/create")
    public String createPlan(@RequestParam Date planId, @RequestParam String planName, @RequestParam(required = false) USer user, @RequestParam(required = false) Company company) {
        planService.createPlan(planId, planName, user, company);
        return "redirect:/plan/all";
    }

    @PostMapping("/update")
    public String updatePlan(@RequestParam int planIndex, @RequestParam Date newPlanId, @RequestParam String newPlanName) {
        boolean success = planService.updatePlan(planIndex, newPlanId, newPlanName);
        if (success) {
            return "redirect:/plan/all";
        } else {
            return "redirect:/plan/error";
        }
    }

    @GetMapping("/delete/{planIndex}")
    public String deletePlan(@PathVariable int planIndex) {
        boolean success = planService.deletePlan(planIndex);
        if (success) {
            return "redirect:/plan/all";
        } else {
            return "redirect:/plan/error";
        }
    }
    // planIndex로 Plan 찾기
    @GetMapping("/{planIndex}")
    public String getPlanByIndex(@PathVariable int planIndex, Model model) {
        Plan plan = planService.getPlanByIndex(planIndex);
        model.addAttribute("plan", plan);
        return "plan/detail";
    }

    // planUsid로 Plan 찾기
    @GetMapping("/user/{userId}")
    public String getPlansByUser(@PathVariable String userId, Model model) {
        USer existuser = userRepository.findUserById(userId).get(0);
        List<Plan> plans = planService.getPlansByUser(existuser);
        model.addAttribute("plans", plans);
        return "plan/list";
    }

    // planCpid로 Plan 찾기
    @GetMapping("/company/{companyId}")
    public String getPlansByCompany(@PathVariable String companyId, Model model) {
        Company company = companyRepository.findCompanyById(companyId).get(0);
        List<Plan> plans = planService.getPlansByCompany(company);
        model.addAttribute("plans", plans);
        return "plan/list";
    }

    // planId와 유저아이디로 Plan 찾기
    @GetMapping("/date-user")
    public String getPlansByDateAndUser(@RequestParam Date planId, @RequestParam String userId, Model model) {
        USer user = userRepository.findUserById(userId).get(0);
        List<Plan> plans = planService.getPlansByDateAndUser(planId, user);
        model.addAttribute("plans", plans);
        return "plan/list";
    }

    // planId와 산업체아이디로 Plan 찾기
    @GetMapping("/date-company")
    public String getPlansByDateAndCompany(@RequestParam Date planId, @RequestParam String companyId, Model model) {
        Company company = companyRepository.findCompanyById(companyId).get(0);
        List<Plan> plans = planService.getPlansByDateAndCompany(planId, company);
        model.addAttribute("plans", plans);
        return "plan/list";
    }

    // 아이디와 월을 입력받아 해당하는 일정 찾기
    @GetMapping("/user-month")
    public String getPlansByUserIdAndMonth(@RequestParam String userId, @RequestParam int month, Model model) {
        List<Plan> plans = planService.getPlansByUserIdAndMonth(userId, month);
        model.addAttribute("plans", plans);
        return "plan/list";
    }


    // 여기에 필요한 나머지 메소드들을 추가하세요.
}
