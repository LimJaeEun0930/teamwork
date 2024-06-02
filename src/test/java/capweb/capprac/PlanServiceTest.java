package capweb.capprac;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class PlanServiceTest {

    @Autowired
    private PlanService planService;

    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;

    private User user;
    private Company company;
    private Plan plan;
    private Plan pplan;
    private Date date;
    @BeforeEach
    void setUp(){
        date = new Date();
        user = new User();
        user.setUsId("usid");
        user.setUsPw("uspw");
        user.setUsName("usname");
        user.setUsJoindate(date);
        user.setUsJoinIP("123.00.1");
        userRepository.save(user);
        company = new Company();
        company.setCpId("cpid");
        company.setCpPw("cppw");
        company.setCpName("cpname");
        company.setCpCategory("cpcate");
        company.setCpAddr("cpaddr");
        company.setCpMtid("cpmtid");
        company.setCpMtname("cpmtname");
        company.setCpJoindate(date);
        company.setCpJoinIP("127.00.1");
        companyRepository.save(company);
        plan = new Plan();
        plan.setPlanId(date);
        plan.setPlanName("planname");
        plan.setPlanOpt(1);
        plan.setPlanUsid(user);
        planRepository.save(plan);
        pplan = new Plan();
        pplan.setPlanId(date);
        pplan.setPlanName("pplanname");
        pplan.setPlanOpt(2);
        pplan.setPlanCpid(company);
        planRepository.save(pplan);
    }

    // Test for createPlan method
    @Test
    public void testCreatePlan() {
        Date planId = new Date();
        String planName = "New Plan";
        User uuser;
        Company ccompany;
        uuser = new User();
        uuser.setUsId("uusid");
        uuser.setUsPw("uuspw");
        uuser.setUsName("uusname");
        uuser.setUsJoindate(date);
        uuser.setUsJoinIP("123.02.1");
        userRepository.save(uuser);
        ccompany = new Company();
        ccompany.setCpId("ccpid");
        ccompany.setCpPw("ccppw");
        ccompany.setCpName("ccpname");
        ccompany.setCpCategory("ccpcate");
        ccompany.setCpAddr("ccpaddr");
        ccompany.setCpMtid("ccpmtid");
        ccompany.setCpMtname("ccpmtname");
        ccompany.setCpJoindate(date);
        ccompany.setCpJoinIP("127.03.1");
        companyRepository.save(ccompany);
        User nonuser;
        Company noncompany;

        Plan plan = planService.createPlan(planId, planName, uuser, null);

        assertThat(plan).isNotNull();
        assertThat(plan.getPlanId()).isEqualTo(planId);
        assertThat(plan.getPlanName()).isEqualTo(planName);
        assertThat(plan.getPlanUsid()).isEqualTo(uuser);
        //assertThat(plan.getPlanCpid()).isEqualTo(company);

        Plan cplan = planService.createPlan(planId, planName, null,ccompany);

        assertThat(cplan).isNotNull();
        assertThat(cplan.getPlanId()).isEqualTo(planId);
        assertThat(cplan.getPlanName()).isEqualTo(planName);
        //assertThat(cplan.getPlanUsid()).isEqualTo(uuser);
        assertThat(cplan.getPlanCpid()).isEqualTo(ccompany);
    }

    // Additional tests for other methods can be structured similarly
    // ...

    // Test for updatePlan method
    @Test
    public void testUpdatePlan() {
        // Assume that a plan with index 1 exists
        int planIndex = 1;
        Date newPlanId = new Date();
        String newPlanName = "Updated Plan";

        boolean result = planService.updatePlan(planIndex, newPlanId, newPlanName);

        assertThat(result).isTrue();
        Plan updatedPlan = planService.getPlanByIndex(planIndex);
        assertThat(updatedPlan.getPlanId()).isEqualTo(newPlanId);
        assertThat(updatedPlan.getPlanName()).isEqualTo(newPlanName);
    }

    // Test for deletePlan method
    @Test
    public void testDeletePlan() {
        // Assume that a plan with index 1 exists
        int planIndex = 1;

        boolean result = planService.deletePlan(planIndex);

        assertThat(result).isTrue();
        //assertThrows(IllegalArgumentException.class, () -> planService.getPlanByIndex(planIndex));
    }

    // Test for getPlanByIndex method
    @Test
    public void testGetPlanByIndex() {
        // Assume that a plan with index 1 exists
        int planIndex = 1;

        Plan plan = planService.getPlanByIndex(planIndex);

        assertThat(plan).isNotNull();
        assertThat(plan.getPlanIndex()).isEqualTo(planIndex);
    }

    // Test for getAllPlans method
    @Test
    public void testGetAllPlans() {
        List<Plan> plans = planService.getAllPlans();

        assertThat(plans).isNotNull();
        assertThat(plans.size()).isGreaterThan(0);
    }

    // Test for getPlansByName method
    @Test
    public void testGetPlansByName() {
        String planName = "planname";

        List<Plan> plans = planService.getPlansByName(planName);

        assertThat(plans).isNotEmpty();
        assertThat(plans.get(0).getPlanName()).isEqualTo(planName);
    }

    // Test for getPlansByUser method
    @Test
    public void testGetPlansByUser() {

        List<Plan> plans = planService.getPlansByUser(user);

        assertThat(plans).isNotEmpty();
        assertThat(plans.get(0).getPlanUsid()).isEqualTo(user);
    }

    // Test for getPlansByCompany method
    @Test
    public void testGetPlansByCompany() {

        List<Plan> plans = planService.getPlansByCompany(company);

        assertThat(plans).isNotEmpty();
        assertThat(plans.get(0).getPlanCpid()).isEqualTo(company);
    }

    // Test for getPlansByDate method
    @Test
    public void testGetPlansByDate() {
        Date planId = date;

        List<Plan> plans = planService.getPlansByDate(planId);

        assertThat(plans).isNotEmpty();
        assertThat(plans.get(0).getPlanId()).isEqualTo(planId);
    }

    // Test for getPlansByOption method
    @Test
    public void testGetPlansByOption() {
        int planOpt = 1;

        List<Plan> plans = planService.getPlansByOption(planOpt);

        assertThat(plans).isNotEmpty();
        assertThat(plans.get(0).getPlanOpt()).isEqualTo(planOpt);
    }

    // Test for getPlansByDateAndUser method
    @Test
    public void testGetPlansByDateAndUser() {

        List<Plan> plans = planService.getPlansByDateAndUser(date, user);

        assertThat(plans).isNotEmpty();
        assertThat(plans.get(0).getPlanId()).isEqualTo(date);
        assertThat(plans.get(0).getPlanUsid()).isEqualTo(user);
    }

    // Test for getPlansByDateAndCompany method
    @Test
    public void testGetPlansByDateAndCompany() {
        List<Plan> plans = planService.getPlansByDateAndCompany(date, company);

        assertThat(plans).isNotEmpty();
        assertThat(plans.get(0).getPlanId()).isEqualTo(date);
        assertThat(plans.get(0).getPlanCpid()).isEqualTo(company);
    }
}
