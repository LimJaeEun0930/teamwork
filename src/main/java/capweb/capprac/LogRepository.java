package capweb.capprac;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class LogRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create - 새로운 Log 저장
    public void save(Log log) {
        entityManager.persist(log);
    }

    // Read - logIndex로 Log 찾기
    public Log findLogByIndex(int logIndex) {
        return entityManager.find(Log.class, logIndex);
    }

    // Read - 모든 Log 찾기
    public List<Log> findAllLogs() {
        TypedQuery<Log> query = entityManager.createQuery("SELECT l FROM Log l", Log.class);
        return query.getResultList();
    }

    // Update - Log 업데이트
    public void update(Log log) {
        entityManager.merge(log);
    }

    // Delete - logIndex로 Log 삭제
    public void deleteByIndex(int logIndex) {
        Log log = findLogByIndex(logIndex);
        if (log != null) {
            entityManager.remove(log);
        }
    }

    // Read - logEnterdate로 Log 찾기
    public List<Log> findLogsByEnterdate(Date logEnterdate) {
        TypedQuery<Log> query = entityManager.createQuery(
                "SELECT l FROM Log l WHERE l.logEnterdate = :logEnterdate", Log.class);
        query.setParameter("logEnterdate", logEnterdate);
        return query.getResultList();
    }

    // Read - logEnterIP로 Log 찾기
    public List<Log> findLogsByEnterIP(String logEnterIP) {
        TypedQuery<Log> query = entityManager.createQuery(
                "SELECT l FROM Log l WHERE l.logEnterIP = :logEnterIP", Log.class);
        query.setParameter("logEnterIP", logEnterIP);
        return query.getResultList();
    }

    // Read - logUsid로 Log 찾기
    public List<Log> findLogsByUser(User logUsid) {
        TypedQuery<Log> query = entityManager.createQuery(
                "SELECT l FROM Log l WHERE l.logUsid = :logUsid", Log.class);
        query.setParameter("logUsid", logUsid);
        return query.getResultList();
    }

    // Read - logCpid로 Log 찾기
    public List<Log> findLogsByCompany(Company logCpid) {
        TypedQuery<Log> query = entityManager.createQuery(
                "SELECT l FROM Log l WHERE l.logCpid = :logCpid", Log.class);
        query.setParameter("logCpid", logCpid);
        return query.getResultList();
    }

    // Read - logOpt로 Log 찾기
    public List<Log> findLogsByOpt(int logOpt) {
        TypedQuery<Log> query = entityManager.createQuery(
                "SELECT l FROM Log l WHERE l.logOpt = :logOpt", Log.class);
        query.setParameter("logOpt", logOpt);
        return query.getResultList();
    }

    // 추가적인 메소드들을 여기에 구현할 수 있습니다.
}
