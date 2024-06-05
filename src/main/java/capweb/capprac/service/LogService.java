package capweb.capprac.service;

import capweb.capprac.entity.Company;
import capweb.capprac.entity.Log;
import capweb.capprac.entity.User;
import capweb.capprac.repository.LogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    // 만들기 - 새로운 Log 생성 및 저장
    //접속일자,접속아이피와 유저아이디,회사아이디를 선택하여 입력받아 만들기
    @Transactional
    public Log createLog(Date logEnterdate, String logEnterIP, User logUsid, Company logCpid) {
        if (logEnterdate == null || logEnterIP == null || logEnterIP.trim().isEmpty()) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
        if(logCpid == null&&logUsid == null) {
            throw new IllegalArgumentException("사용자이거나 회사 중 선택");
        }
        Log log = new Log();
        log.setLogEnterdate(logEnterdate);
        log.setLogEnterIP(logEnterIP);
        if (logUsid != null) {
            log.setLogUsid(logUsid);
            log.setLogOpt(1);
        } else if (logCpid != null) {
            log.setLogCpid(logCpid);
            log.setLogOpt(2);
        } else {
            throw new IllegalArgumentException("유저 아이디와 산업체 아이디 중 하나는 반드시 제공되어야 합니다.");
        }
        logRepository.save(log);
        return log;
    }

    // 조회 - 모든 Log 찾기
    public List<Log> getAllLogs() {
        return logRepository.findAllLogs();
    }

    // 조회 - logIndex로 Log 찾기
    public Log getLogByIndex(int logIndex) {
        return logRepository.findLogByIndex(logIndex);
    }

    // 조회 - logEnterdate로 Log 찾기
    public List<Log> getLogsByEnterdate(Date logEnterdate) {
        return logRepository.findLogsByEnterdate(logEnterdate);
    }

    // 조회 - logEnterIP로 Log 찾기
    public List<Log> getLogsByEnterIP(String logEnterIP) {
        return logRepository.findLogsByEnterIP(logEnterIP);
    }

    // 조회 - logUsid로 Log 찾기
    public List<Log> getLogsByUser(User logUsid) {
        return logRepository.findLogsByUser(logUsid);
    }

    // 조회 - logCpid로 Log 찾기
    public List<Log> getLogsByCompany(Company logCpid) {
        return logRepository.findLogsByCompany(logCpid);
    }

    // 조회 - logOpt로 Log 찾기
    public List<Log> getLogsByOpt(int logOpt) {
        return logRepository.findLogsByOpt(logOpt);
    }

    // 추가적인 서비스 메소드들을 여기에 구현할 수 있습니다.
}
