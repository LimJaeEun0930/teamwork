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
public class LogServiceTest {

    @Autowired
    private LogService logService;
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

    // Test for createLog method
    @Test
    public void testCreateLog() {
        Date logEnterdate = new Date();
        String logEnterIP = "192.168.1.1";

        Log log = logService.createLog(logEnterdate, logEnterIP, user, null);

        assertThat(log).isNotNull();
        assertThat(log.getLogEnterdate()).isEqualTo(logEnterdate);
        assertThat(log.getLogEnterIP()).isEqualTo(logEnterIP);
        assertThat(log.getLogUsid()).isEqualTo(user);
        assertThat(log.getLogCpid()).isEqualTo(null);

        Log llog = logService.createLog(logEnterdate, logEnterIP, null,company);

        assertThat(llog).isNotNull();
        assertThat(llog.getLogEnterdate()).isEqualTo(logEnterdate);
        assertThat(llog.getLogEnterIP()).isEqualTo(logEnterIP);
        assertThat(llog.getLogUsid()).isEqualTo(null);
        assertThat(llog.getLogCpid()).isEqualTo(company);
    }

    // Additional tests for other methods can be structured similarly
    // ...

    // Test for getAllLogs method
    @Test
    public void testGetAllLogs() {
        List<Log> logs = logService.getAllLogs();

        assertThat(logs).isNotNull();
        assertThat(logs.size()).isGreaterThan(0);
    }

    // Test for getLogByIndex method
    @Test
    public void testGetLogByIndex() {
        // Assume that a log with index 1 exists
        int logIndex = 1;

        Log log = logService.getLogByIndex(logIndex);

        assertThat(log).isNotNull();
        assertThat(log.getLogIndex()).isEqualTo(logIndex);
    }

    // Test for getLogsByEnterdate method
    @Test
    public void testGetLogsByEnterdate() {
        Date logEnterdate = date;

        List<Log> logs = logService.getLogsByEnterdate(logEnterdate);

        assertThat(logs).isNotEmpty();
        assertThat(logs.get(0).getLogEnterdate()).isEqualTo(logEnterdate);
    }

    // Test for getLogsByEnterIP method
    @Test
    public void testGetLogsByEnterIP() {
        String logEnterIP = "127.00.1";

        List<Log> logs = logService.getLogsByEnterIP(logEnterIP);

        assertThat(logs).isNotEmpty();
        //assertThat(logs.get(0).getLogEnterIP()).isEqualTo(logEnterIP);
    }

    // Test for getLogsByUser method
    @Test
    public void testGetLogsByUser() {

        List<Log> logs = logService.getLogsByUser(user);

        assertThat(logs).isNotEmpty();
        assertThat(logs.get(0).getLogUsid()).isEqualTo(user);
    }

    // Test for getLogsByCompany method
    @Test
    public void testGetLogsByCompany() {

        List<Log> logs = logService.getLogsByCompany(company);

        assertThat(logs).isNotEmpty();
        assertThat(logs.get(0).getLogCpid()).isEqualTo(company);
    }

    // Test for getLogsByOpt method
    @Test
    public void testGetLogsByOpt() {
        int logOpt = 1;

        List<Log> logs = logService.getLogsByOpt(logOpt);

        assertThat(logs).isNotEmpty();
        assertThat(logs.get(0).getLogOpt()).isEqualTo(logOpt);
    }
}
