package capweb.capprac.controller;

import capweb.capprac.dto.PlanCreateFormData;
import capweb.capprac.entity.Company;
import capweb.capprac.entity.USer;
import capweb.capprac.util.CreatePlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import capweb.capprac.entity.Plan;
import capweb.capprac.service.PlanService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
//생성,수정,삭제가 가능하나 수정이나 삭제는 html에서 해당 일정의 인덱스값을 넘겨줘야하는데? 가능한지 모르겠어요...ㅠㅜㅠㅜ
//1.따라서 html 수정,삭제 폼에서 해당 일정의 인덱스값을 찾아서 인덱스를 넘겨주는 동작 필요!!!!!
//2.조회 또한 html에서 띄우는 법을 잘 모르겠네요 ㅠㅜ
@Controller
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private CreatePlan createPlan;

    @Autowired
    private PlanService planService;

    // 일정 생성 0609
    @PostMapping("/create")
    public String createPlan(@ModelAttribute PlanCreateFormData planCreateFormData) {
        Plan plan = createPlan.createPlan(planCreateFormData);
        planService.createPlan(plan);
        return "redirect:/plans";
    }

    // 일정 수정 0609-- html에서 수정필요!!!!!!!html에서 인덱스번호를 어떻게 줘야하는지는 모르겠음!!!!!
    @PostMapping("/update/{planIndex}")
    public String updatePlan(@PathVariable int planIndex,@ModelAttribute PlanCreateFormData planCreateFormData) {
        Plan existingPlan = planService.getPlanByIndex(planIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date planDate = sdf.parse(planCreateFormData.getPlanId());
            existingPlan.setPlanId(planDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        existingPlan.setPlanName(planCreateFormData.getPlanName());
        planService.updatePlan(existingPlan);
        return "redirect:/plans";
    }

    // 일정 삭제-0609    html에서 수정필요!!!!!!!html에서 인덱스번호를 어떻게 줘야하는지는 모르겠음!!!!!
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

    @GetMapping("/all")
    public String getAllPlans(Model model) {
        List<Plan> plans = planService.getAllPlans(); // DB에서 모든 일정을 가져옵니다.
        model.addAttribute("plans", plans); // 모델에 일정 목록을 추가합니다.
        return "board/PlanHtml"; // Thymeleaf 템플릿 이름을 반환합니다.
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
