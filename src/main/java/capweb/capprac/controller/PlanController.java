package capweb.capprac.controller;

import capweb.capprac.entity.Company;
import capweb.capprac.entity.USer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import capweb.capprac.entity.Plan;
import capweb.capprac.service.PlanService;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private PlanService planService;

    // 일정 생성
    @PostMapping("/create")
    public String createPlan(@ModelAttribute Plan plan) {
        planService.createPlan(plan);
        return "redirect:/plans";
    }

    // 일정 수정
    @PostMapping("/update/{planIndex}")
    public String updatePlan(@PathVariable int planIndex, @ModelAttribute Plan plan) {
        planService.updatePlan(plan);
        return "redirect:/plans";
    }

    // 일정 삭제
    @GetMapping("/delete/{planIndex}")
    public String deletePlan(@PathVariable int planIndex) {
        planService.deletePlan(planIndex);
        return "redirect:/plans";
    }

    // planIndex로 Plan 찾기
    @GetMapping("/{planIndex}")
    @ResponseBody
    public Plan getPlanByIndex(@PathVariable int planIndex) {
        return planService.getPlanByIndex(planIndex);
    }

    // 모든 Plan 찾기
    @GetMapping("/all")
    @ResponseBody
    public List<Plan> getAllPlans() {
        return planService.getAllPlans();
    }

    // planName으로 Plan 찾기
    @GetMapping("/name/{planName}")
    @ResponseBody
    public List<Plan> getPlansByName(@PathVariable String planName) {
        return planService.getPlansByName(planName);
    }

    // planUsid로 Plan 찾기
    @GetMapping("/user/{planUsid}")
    @ResponseBody
    public List<Plan> getPlansByUser(@PathVariable USer planUsid) {
        return planService.getPlansByUser(planUsid);
    }

    // planCpid로 Plan 찾기
    @GetMapping("/company/{planCpid}")
    @ResponseBody
    public List<Plan> getPlansByCompany(@PathVariable Company planCpid) {
        return planService.getPlansByCompany(planCpid);
    }

    // planId(날짜)로 Plan 찾기
    @GetMapping("/date/{planId}")
    @ResponseBody
    public List<Plan> getPlansByDate(@PathVariable Date planId) {
        return planService.getPlansByDate(planId);
    }

    // planOpt로 Plan 찾기
    @GetMapping("/option/{planOpt}")
    @ResponseBody
    public List<Plan> getPlansByOption(@PathVariable int planOpt) {
        return planService.getPlansByOption(planOpt);
    }

    // planId와 유저아이디로 Plan 찾기
    @GetMapping("/date-user/{planId}/{planUsid}")
    @ResponseBody
    public List<Plan> getPlansByDateAndUser(@PathVariable Date planId, @PathVariable USer planUsid) {
        return planService.getPlansByDateAndUser(planId, planUsid);
    }

    // planId와 산업체아이디로 Plan 찾기
    @GetMapping("/date-company/{planId}/{planCpid}")
    @ResponseBody
    public List<Plan> getPlansByDateAndCompany(@PathVariable Date planId, @PathVariable Company planCpid) {
        return planService.getPlansByDateAndCompany(planId, planCpid);
    }

    // 아이디와 월을 입력받아 해당하는 일정 찾기
    @GetMapping("/user-month/{userId}/{month}")
    @ResponseBody
    public List<Plan> getPlansByUserIdAndMonth(@PathVariable String userId, @PathVariable int month) {
        return planService.getPlansByUserIdAndMonth(userId, month);
    }
}
