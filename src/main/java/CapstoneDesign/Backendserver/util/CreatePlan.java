package CapstoneDesign.Backendserver.util;

import CapstoneDesign.Backendserver.domain.dto.PlanCreateFormData;
import CapstoneDesign.Backendserver.domain.User;
import CapstoneDesign.Backendserver.domain.Company;
import CapstoneDesign.Backendserver.domain.Plan;
import CapstoneDesign.Backendserver.repository.CompanyRepository;
import CapstoneDesign.Backendserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class CreatePlan {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public Plan createPlan(PlanCreateFormData planCreateFormData) {
        int plansel=0;
        Plan plan = new Plan();

        plan.setPlanName(planCreateFormData.getPlanName());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date planDate = sdf.parse(planCreateFormData.getPlanId());
            plan.setPlanId(planDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Optional<User> user = userRepository.findById(planCreateFormData.getPlanUsid());
        if (user.isPresent()) {
            plan.setPlanUsid(user.get());
            plansel=1;
        }

        Optional<Company> company = companyRepository.findCompanyById(planCreateFormData.getPlanCpid());
        if (company.isPresent()) {
            plan.setPlanCpid(company.get());
            plansel=1;
        }
        if (plansel==0) {
            throw new IllegalStateException("user and company are empty or not empty");
        }
        return plan;

    }
}
