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
public class PlanRepositoryTest {

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
    // Test for save method
    @Test
    public void testSave() {
        assertThat(planRepository.findPlanByIndex(plan.getPlanIndex())).isNotNull();
    }

    // Test for findPlanByIndex method
    @Test
    public void testFindPlanByIndex() {
        // Assume that a plan with index 1 exists
        int planIndex = 1;

        Plan plans = planRepository.findPlanByIndex(planIndex);

        assertThat(plans).isNotNull();
        assertThat(plans.getPlanIndex()).isEqualTo(planIndex);
    }

    // Test for findAllPlans method
    @Test
    public void testFindAllPlans() {
        List<Plan> plans = planRepository.findAllPlans();

        assertThat(plans).isNotNull();
        assertThat(plans.size()).isGreaterThan(0);
    }

    // Test for update method
    @Test
    public void testUpdate() {
        // Assume that a plan with index 1 exists
        Plan plans = planRepository.findPlanByIndex(1);
        // Modify properties of plan
        planRepository.update(plans);

        Plan updatedPlan = planRepository.findPlanByIndex(plans.getPlanIndex());
        // Verify the properties are updated
        assertThat(updatedPlan).isEqualToComparingFieldByField(plans);
    }

    // Test for deleteByIndex method
    @Test
    public void testDeleteByIndex() {
        // Assume that a plan with index 1 exists
        int planIndex = 1;

        planRepository.deleteByIndex(planIndex);

        assertThat(planRepository.findPlanByIndex(planIndex)).isNull();

        //assertThrows(IllegalArgumentException.class, () -> planRepository.findPlanByIndex(planIndex));
    }

    // Test for findPlansByName method
    @Test
    public void testFindPlansByName() {
        String planName = "planname";

        List<Plan> plans = planRepository.findPlansByName(planName);

        assertThat(plans).isNotEmpty();
        assertThat(plans.get(0).getPlanName()).isEqualTo(planName);
    }

    // Test for findPlansByUser method
    @Test
    public void testFindPlansByUser() {
        List<Plan> plans = planRepository.findPlansByUser(user);

        assertThat(plans).isNotEmpty();
        assertThat(plans.get(0).getPlanUsid()).isEqualTo(user);
    }

    // Test for findPlansByCompany method
    @Test
    public void testFindPlansByCompany() {

        List<Plan> plans = planRepository.findPlansByCompany(company);

        assertThat(plans).isNotEmpty();
        //assertThat(plans.get(0).getPlanCpid()).isEqualTo(company);
    }

    // Test for findPlansByDate method
    @Test
    public void testFindPlansByDate() {
        Date planDate = date;

        List<Plan> plans = planRepository.findPlansByDate(planDate);

        assertThat(plans).isNotEmpty();
        assertThat(plans.get(0).getPlanId()).isEqualTo(planDate);
    }

    // Test for findPlansByOption method
    @Test
    public void testFindPlansByOption() {
        int planOpt = 1;

        List<Plan> plans = planRepository.findPlansByOption(planOpt);

        assertThat(plans).isNotEmpty();
        assertThat(plans.get(0).getPlanOpt()).isEqualTo(planOpt);
    }

    // Test for findPlansByDateAndUser method
    @Test
    public void testFindPlansByDateAndUser() {
        Date planDate = date;
        List<Plan> plans = planRepository.findPlansByDateAndUser(planDate, user);

        assertThat(plans).isNotEmpty();
        assertThat(plans.get(0).getPlanId()).isEqualTo(planDate);
        assertThat(plans.get(0).getPlanUsid()).isEqualTo(user);
    }

    // Test for findPlansByDateAndCompany method
    @Test
    public void testFindPlansByDateAndCompany() {
        Date planDate = date;

        List<Plan> plans = planRepository.findPlansByDateAndCompany(planDate, company);

        assertThat(plans).isNotEmpty();
        assertThat(plans.get(0).getPlanId()).isEqualTo(planDate);
        assertThat(plans.get(0).getPlanCpid()).isEqualTo(company);
    }
}
