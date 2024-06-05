package capweb.capprac.repository;

import capweb.capprac.entity.Company;
import capweb.capprac.entity.Log;
import capweb.capprac.entity.User;
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
public class LogRepositoryTest {

    @Autowired
    private LogRepository logRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;

    private User user;
    private Company company;
    private Log log;
    private Log clog;
    private Date date;
    @BeforeEach
    public void setUp() {
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
        log = new Log();
        log.setLogUsid(user);
        log.setLogEnterdate(date);
        log.setLogEnterIP("127.00.1");
        log.setLogOpt(1);
        logRepository.save(log);
        clog = new Log();
        clog.setLogCpid(company);
        clog.setLogEnterdate(date);
        clog.setLogEnterIP("128.00.1");
        clog.setLogOpt(2);
        logRepository.save(clog);
    }

    // Test for save method
    @Test
    public void testSave() {

        assertThat(logRepository.findLogByIndex(log.getLogIndex())).isNotNull();
    }

    // Test for findLogByIndex method
    @Test
    public void testFindLogByIndex() {
        // Assume that a log with index 1 exists
        int logIndex = 1;

        Log log = logRepository.findLogByIndex(logIndex);

        assertThat(log).isNotNull();
        assertThat(log.getLogIndex()).isEqualTo(logIndex);
    }

    // Test for findAllLogs method
    @Test
    public void testFindAllLogs() {
        List<Log> logs = logRepository.findAllLogs();

        assertThat(logs).isNotNull();
        assertThat(logs.size()).isGreaterThan(0);
    }

    // Test for update method
    @Test
    public void testUpdate() {
        // Assume that a log with index 1 exists
        User uuser;
        uuser = new User();
        uuser.setUsId("uusid");
        uuser.setUsPw("uuspw");
        uuser.setUsName("uusname");
        uuser.setUsJoindate(date);
        uuser.setUsJoinIP("133.00.1");
        userRepository.save(uuser);
        Log llog = logRepository.findLogByIndex(1);
        llog.setLogUsid(uuser);
        // Modify properties of log
        logRepository.update(llog);

        Log updatedLog = logRepository.findLogByIndex(log.getLogIndex());
        // Verify the properties are updated
        assertThat(updatedLog).isNotNull();
        //assertThat(updatedLog).isEqualToComparingFieldByField(log);
    }

    // Test for deleteByIndex method
    @Test
    public void testDeleteByIndex() {
        // Assume that a log with index 1 exists
        int logIndex = 1;

        logRepository.deleteByIndex(logIndex);
        assertThat(logRepository.findLogByIndex(logIndex)).isNull();
        //assertThrows(IllegalArgumentException.class, () -> logRepository.findLogByIndex(logIndex));
    }

    // Test for findLogsByEnterdate method
    @Test
    public void testFindLogsByEnterdate() {
        Date logEnterdate = date;

        List<Log> logs = logRepository.findLogsByEnterdate(logEnterdate);

        assertThat(logs).isNotEmpty();
        assertThat(logs.get(0).getLogEnterdate()).isEqualTo(logEnterdate);
    }

    // Test for findLogsByEnterIP method
    @Test
    public void testFindLogsByEnterIP() {
        String logEnterIP = "127.00.1";

        List<Log> logs = logRepository.findLogsByEnterIP(logEnterIP);

        assertThat(logs).isNotEmpty();
        assertThat(logs.get(0).getLogEnterIP()).isEqualTo(logEnterIP);
    }

    // Test for findLogsByUser method
    @Test
    public void testFindLogsByUser() {

        List<Log> logs = logRepository.findLogsByUser(user);

        assertThat(logs).isNotEmpty();
        assertThat(logs.get(0).getLogUsid()).isEqualTo(user);
    }

    // Test for findLogsByCompany method
    @Test
    public void testFindLogsByCompany() {

        List<Log> logs = logRepository.findLogsByCompany(company);

        assertThat(logs).isNotEmpty();
        assertThat(logs.get(0).getLogCpid()).isEqualTo(company);
    }

    // Test for findLogsByOpt method
    @Test
    public void testFindLogsByOpt() {
        int logOpt = 1;

        List<Log> logs = logRepository.findLogsByOpt(logOpt);

        assertThat(logs).isNotEmpty();
        assertThat(logs.get(0).getLogOpt()).isEqualTo(logOpt);
    }
}
