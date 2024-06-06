package capweb.capprac.service;

import capweb.capprac.entity.Anmp;
import capweb.capprac.entity.Announcement;
import capweb.capprac.entity.User;
import capweb.capprac.repository.AnmpRepository;
import capweb.capprac.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AnmpService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnmpRepository anmpRepository;

    // 만들기 - 새로운 Anmp 생성 및 저장
    //사용자가 동일 공지 참여 못하게 막고, 사용자아이디와 공지아이디를 반드시 입력
    @Transactional
    public Anmp createAnmp(User anmpUsid, Announcement anmpAnmid) {
        List<Anmp>existanmps = anmpRepository.findAnmpsByUserAndAnnouncement(anmpUsid,anmpAnmid);
        if(!existanmps.isEmpty()){
            throw new IllegalStateException("사용자는 이미 이 공지에 참여하고 있습니다.");
        }
        if (anmpUsid == null || anmpAnmid == null) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
        Anmp anmp = new Anmp();
        anmp.setAnmpUsid(anmpUsid);
        anmp.setAnmpAnmid(anmpAnmid);
        anmpRepository.save(anmp);
        return anmp;
    }


    // 조회 - 모든 Anmp 찾기
    public List<Anmp> getAllAnmps() {
        return anmpRepository.findAllAnmps();
    }

    // 조회 - anmpIndex로 Anmp 찾기
    public Anmp getAnmpByIndex(int anmpIndex) {
        return anmpRepository.findAnmpByIndex(anmpIndex);
    }

    // 조회 - anmpUsid로 Anmp 찾기
    public List<Anmp> getAnmpsByUser(User anmpUsid) {
        return anmpRepository.findAnmpsByUser(anmpUsid);
    }

    // 조회 - anmpAnmid로 Anmp 찾기
    public List<Anmp> getAnmpsByAnnouncement(Announcement anmpAnmid) {
        return anmpRepository.findAnmpsByAnnouncement(anmpAnmid);
    }

    // 조회 - anmpUsid와 anmpAnmid 조합으로 Anmp 찾기
    public List<Anmp> getAnmpsByUserAndAnnouncement(User anmpUsid, Announcement anmpAnmid) {
        return anmpRepository.findAnmpsByUserAndAnnouncement(anmpUsid, anmpAnmid);
    }

    // 삭제 - anmpIndex로 Anmp 삭제
    @Transactional
    public boolean deleteAnmp(int anmpIndex) {
        Anmp danmp = anmpRepository.findAnmpByIndex(anmpIndex);
        if(danmp!=null) {
            anmpRepository.deleteByIndex(anmpIndex);
            return true;
        }
        return false;
    }

    //조회- 유저아이디와 기간을 입력받아 참여한 공지 찾기(널이거나 아이디가 없을때 예외처리)
    public List<Anmp> getUserAnnouncements(String userId, Date startDate, Date endDate) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
         List<User>existuser =  userRepository.findUserById(userId);
        if(existuser.isEmpty()){
            throw new IllegalArgumentException("User ID not found");
        }
        List<Anmp>findanmps=anmpRepository.findAnmpsByUserAndDateRange(userId, startDate, endDate);
        if(findanmps.isEmpty()){
            throw new IllegalArgumentException("anmp not found");
        }
        else{
            return findanmps;
        }
    }
    //유저아이디와 월을 입력받아 해당하는 담은 공지 찾기
    public List<Anmp> getAnnouncementsByUserIdAndMonth(String userId, int month) {
        List<User>findusers=userRepository.findUserById(userId);
        if(findusers.isEmpty()){
            throw new IllegalArgumentException("users not found");
        }
        List<Anmp>findanmps=anmpRepository.findAnnouncementsByUserIdAndMonth(userId, month);
        if(findanmps.isEmpty()){
            throw new IllegalStateException("anmps not found");
        }
        else{
            return findanmps;
        }
    }

    // 추가적인 서비스 메소드들을 여기에 구현할 수 있습니다.
}
