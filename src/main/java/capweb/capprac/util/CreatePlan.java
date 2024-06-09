package capweb.capprac.util;

import capweb.capprac.dto.PlanCreateFormData;
import capweb.capprac.entity.Company;
import capweb.capprac.entity.Plan;
import capweb.capprac.entity.USer;
import capweb.capprac.repository.CompanyRepository;
import capweb.capprac.repository.USerRepository;
import capweb.capprac.service.CompanyService;
import capweb.capprac.service.USerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class CreatePlan {

    @Autowired
    private USerRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public Plan createPlan(PlanCreateFormData planCreateFormData) {
        Plan plan = new Plan();

        plan.setPlanName(planCreateFormData.getPlanName());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date planDate = sdf.parse(planCreateFormData.getPlanId());
            plan.setPlanId(planDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<USer> user = userRepository.findUserById(planCreateFormData.getPlanUsid());
        if (user.size() == 0) {
            throw new IllegalStateException("user not found");
        }
        plan.setPlanUsid(user.get(0));

        List<Company> company = companyRepository.findCompanyById(planCreateFormData.getPlanCpid());
        if (company.size() == 0) {
            throw new IllegalStateException("company not found");
        }
        plan.setPlanCpid(company.get(0));

        return plan;

    }
}
