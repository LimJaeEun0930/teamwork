package capweb.capprac.service;

//import CapstoneDesign.Backendserver.repository.UserRepository;
import capweb.capprac.entity.Company;
import capweb.capprac.entity.Plan;
import capweb.capprac.entity.USer;
import capweb.capprac.repository.CompanyRepository;
import capweb.capprac.repository.PlanRepository;
import capweb.capprac.repository.USerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Date;
import java.util.List;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private USerRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;

    // 일정 생성 - 필수값인 일정아이디와 일정명을 받고, 유저와 회사는 선택적으로 받음
    @Transactional
    public Plan createPlan(Plan plan) {
        // 필수값 체크
        if (plan.getPlanId() == null || plan.getPlanName().trim().isEmpty()) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }

        // 유저 또는 회사 제공 여부 확인
        if (plan.getPlanUsid() == null && plan.getPlanCpid() == null) {
            throw new IllegalArgumentException("유저 또는 회사 중 하나는 제공되어야 합니다.");
        }

        // 유저 또는 회사에 따라 옵션 설정
        plan.setPlanOpt(plan.getPlanUsid() != null ? 1 : 2);

        planRepository.save(plan);
        return plan;
    }

    // 일정 수정 - planIndex를 사용하여 Plan의 planId와 planName 수정
    //06/10 수정 내용 - planId수정 불가하게 하기
    @Transactional
    public boolean updatePlan(Plan plan) {
        Plan existingPlan = planRepository.findPlanByIndex(plan.getPlanIndex());
        if (existingPlan != null) {
            existingPlan.setPlanName(plan.getPlanName());
            planRepository.update(existingPlan);
            return true;
        }
        return false;
    }

    // 일정 삭제 - planIndex를 사용하여 Plan 삭제
    @Transactional
    public boolean deletePlan(int planIndex) {
        Plan plan = planRepository.findPlanByIndex(planIndex);
        if (plan != null) {
            planRepository.deleteByIndex(plan.getPlanIndex());
            return true;
        }
        return false;
    }
    // 조회 - planIndex로 Plan 찾기
    public Plan getPlanByIndex(int planIndex) {
        return planRepository.findPlanByIndex(planIndex);
    }

    // 조회 - 모든 Plan 찾기
    public List<Plan> getAllPlans() {
        return planRepository.findAllPlans();
    }

    // 조회 - planName으로 Plan 찾기
    public List<Plan> getPlansByName(String planName) {
        return planRepository.findPlansByName(planName);
    }

    // 조회 - planUsid로 Plan 찾기-----!!!조회에 필요!!!!
    public List<Plan> getPlansByUser(USer planUsid) {
        return planRepository.findPlansByUser(planUsid);
    }

    // 조회 - planCpid로 Plan 찾기------!!!!조회에 필요!!!!!
    public List<Plan> getPlansByCompany(Company planCpid) {
        return planRepository.findPlansByCompany(planCpid);
    }

    // 조회 - planId(날짜)로 Plan 찾기
    public List<Plan> getPlansByDate(Date planId) {
        return planRepository.findPlansByDate(planId);
    }

    // 조회 - planOpt로 Plan 찾기
    public List<Plan> getPlansByOption(int planOpt) {
        return planRepository.findPlansByOption(planOpt);
    }

    // planId와 유저아이디로 Plan 찾기----조회에 필요----
    public List<Plan> getPlansByDateAndUser(Date planId, USer planUsid) {
        return planRepository.findPlansByDateAndUser(planId, planUsid);
    }

    // planId와 산업체아이디로 Plan 찾기-----조회에 필요----
    public List<Plan> getPlansByDateAndCompany(Date planId, Company planCpid) {
        return planRepository.findPlansByDateAndCompany(planId, planCpid);
    }
    //아이디와 월을 입력받아 아이디 체크하고  해당하는 일정 찾기----!!!조회할때 필요!!!
    public List<Plan> getPlansByUserIdAndMonth(String userId, int month) {
        List<USer>existusers=userRepository.findUserById(userId);
        List<Company>existcompanies=companyRepository.findCompanyById(userId);
        if(existusers.isEmpty()&&existcompanies.isEmpty()){
            throw new IllegalArgumentException("User not found");
        }

        List<Plan>findplans=planRepository.findPlansByUserIdAndMonth(userId, month);
        if(findplans.isEmpty()){
            throw new IllegalArgumentException("plans not found");
        }
        else return findplans;
    }

    // 추가적인 서비스 메소드들...
}
