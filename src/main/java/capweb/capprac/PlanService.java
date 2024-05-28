package capweb.capprac;

import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }
    //만들기
    public Plan createPlan(Date planId, String planName, User user, Company company) {
        Plan plan = new Plan();
        plan.setPlanId(planId);
        plan.setPlanName(planName);
        if (user != null) {
            plan.setPlanUsid(user); // 유저 아이디가 비어있지 않을 경우
            plan.setPlanOpt(1);
        } else if (company != null) {
            plan.setPlanCpid(company); // 산업체 아이디가 비어있지 않을 경우
            plan.setPlanOpt(2);
        }
        planRepository.save(plan);
        return plan;
    }


    // 수정 - Plan의 planId와 planName 수정
    public boolean updatePlan(int planIndex, Date newPlanId, String newPlanName) {
        Plan plan = planRepository.findPlanByIndex(planIndex);
        if (plan != null) {
            plan.setPlanId(newPlanId);
            plan.setPlanName(newPlanName);
            planRepository.update(plan);
            return true; // 수정 성공
        }
        return false; // 해당 인덱스의 Plan이 없으면 수정 실패
    }

    // 삭제 - planIndex로 Plan 삭제
    public boolean deletePlan(int planIndex) {
        Plan plan = planRepository.findPlanByIndex(planIndex);
        if (plan != null) {
            planRepository.deleteByIndex(planIndex);
            return true; // 삭제 성공
        }
        return false; // 해당 인덱스의 Plan이 없으면 삭제 실패
    }

    // 추가적인 서비스 메소드들...
}
