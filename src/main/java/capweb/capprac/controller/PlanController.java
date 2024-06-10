package capweb.capprac.controller;

import CapstoneDesign.Backendserver.repository.UserRepository;
import capweb.capprac.dto.PlanCreateFormData;
import capweb.capprac.entity.Company;
import capweb.capprac.entity.USer;
import capweb.capprac.repository.PlanRepository;
import capweb.capprac.repository.USerRepository;
import capweb.capprac.util.CreatePlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import capweb.capprac.entity.Plan;
import capweb.capprac.service.PlanService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private CreatePlan createPlan;

    @Autowired
    private PlanService planService;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private USerRepository userRepository;

    @GetMapping
    public String planPage(Model model) {
        List<Plan> plans = planService.getAllPlans();
        model.addAttribute("plans", plans);
        return "board/PlanHtml";
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
        List<USer> users = userRepository.findUserById(userId);
        if (!users.isEmpty()) {
            List<Plan> plans = planRepository.findPlansByUser(users.get(0));
            model.addAttribute("plans", plans);
        } else {
            model.addAttribute("plans", Collections.emptyList());
        }
        return "board/PlanList";
    }

    // Other methods remain unchanged...
}
