package CapstoneDesign.Backendserver.controller;
//0610 남은 구현할 일: html에서 목록에서 특정 일정을 선택하면 해당 일정의 인덱스를 알아내서 컨트롤러로 넘겨주기

import CapstoneDesign.Backendserver.domain.Company;
import CapstoneDesign.Backendserver.domain.Plan;
import CapstoneDesign.Backendserver.domain.User;
import CapstoneDesign.Backendserver.domain.dto.PlanCreateFormData;
import CapstoneDesign.Backendserver.repository.CompanyRepository;
import CapstoneDesign.Backendserver.repository.PlanRepository;
import CapstoneDesign.Backendserver.repository.UserRepository;
import CapstoneDesign.Backendserver.service.PlanService;
import CapstoneDesign.Backendserver.util.CreatePlan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {


    private final CreatePlan createPlan;


    private final PlanService planService;

    private final PlanRepository planRepository;

    private final UserRepository userRepository;

    private final CompanyRepository companyRepository;

    @GetMapping
    public String planPage(Model model) {
        return "PlanHtml";
    }
    @PostMapping("/selectPlan")
    public String selectPlan(@RequestParam("selectedPlan") int planIndex, Model model) {
        Plan selectedPlan = planRepository.findPlanByIndex(planIndex);
        if (selectedPlan != null) {
            model.addAttribute("selectedPlan", selectedPlan);
            return "PlanHtml"; // 수정 및 삭제 폼이 포함된 뷰로 리턴합니다.
        } else {
            model.addAttribute("message", "선택한 일정을 찾을 수 없습니다.");
            return "PlanHtml"; // 오류 메시지를 포함하여 동일한 뷰로 리턴합니다.
        }
    }


    @PostMapping("/create")
    public String createPlan(@ModelAttribute PlanCreateFormData planCreateFormData, Model model) {
        Plan plan = createPlan.createPlan(planCreateFormData);
        planService.createPlan(plan);
        model.addAttribute("plan", plan);
        return "redirect:/plans";
    }

    @PostMapping("/update/{planIndex}")
    public String updatePlan(@PathVariable int planIndex, @ModelAttribute PlanCreateFormData planCreateFormData, Model model) {
        Plan existingPlan = planService.getPlanByIndex(planIndex);
        existingPlan.setPlanName(planCreateFormData.getPlanName());
        planService.updatePlan(existingPlan);
        model.addAttribute("plan", existingPlan);
        return "redirect:/plans";
    }

    @GetMapping("/delete/{planIndex}")
    public String deletePlan(@PathVariable int planIndex, Model model) {
        planService.deletePlan(planIndex);
        return "redirect:/plans";
    }

    @GetMapping("/user")
    public String getPlansByUserId(@RequestParam String userId, Model model) {
        Optional<User> users = userRepository.findById(userId);
        if (users.isPresent()) {
            List<Plan> plans = planRepository.findPlansByUser(users.get());
            model.addAttribute("plans", plans);
        }
        return "PlanHtml";
    }

    @GetMapping("/company")
    public String getPlansByCompanyId(@RequestParam String companyId, Model model) {
        Optional<Company>companies=companyRepository.findCompanyById(companyId);
        if (companies.isPresent()) {
            List<Plan> plans = planRepository.findPlansByCompany(companies.get());
            model.addAttribute("plans", plans);
        } else {
            model.addAttribute("plans", Collections.emptyList());
        }
        return "PlanHtml";
    }

    // Other methods remain unchanged...
}
