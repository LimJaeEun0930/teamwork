package capweb.capprac.service;

import capweb.capprac.entity.Anmp;
import capweb.capprac.entity.Announcement;
import capweb.capprac.entity.USer;
import capweb.capprac.repository.AnmpRepository;
import capweb.capprac.repository.USerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Date;
import java.util.List;

@Service
public class AnmpService {

    @Autowired
    private USerRepository userRepository;
    @Autowired
    private AnmpRepository anmpRepository;

    // 만들기 - 새로운 Anmp 생성 및 저장
    @Transactional
    public Anmp createAnmp(@ModelAttribute Anmp anmp) {
        if (anmp.getAnmpUsid() == null || anmp.getAnmpAnmid() == null) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }

        List<Anmp> existanmps = anmpRepository.findAnmpsByUserAndAnnouncement(anmp.getAnmpUsid(), anmp.getAnmpAnmid());
        if (!existanmps.isEmpty()) {
            throw new IllegalStateException("사용자는 이미 이 공지에 참여하고 있습니다.");
        }

        anmpRepository.save(anmp);
        return anmp;
    }

    // 삭제 - anmpIndex로 Anmp 삭제
    @Transactional
    public boolean deleteAnmp(int anmpIndex) {
        Anmp anmp = anmpRepository.findAnmpByIndex(anmpIndex);
        if (anmp != null) {
            anmpRepository.deleteByIndex(anmpIndex);
            return true;
        }
        return false;
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
    public List<Anmp> getAnmpsByUser(USer anmpUsid) {
        return anmpRepository.findAnmpsByUser(anmpUsid);
    }

    // 조회 - anmpAnmid로 Anmp 찾기
    public List<Anmp> getAnmpsByAnnouncement(Announcement anmpAnmid) {
        return anmpRepository.findAnmpsByAnnouncement(anmpAnmid);
    }

    // 조회 - anmpUsid와 anmpAnmid 조합으로 Anmp 찾기
    public List<Anmp> getAnmpsByUserAndAnnouncement(USer anmpUsid, Announcement anmpAnmid) {
        return anmpRepository.findAnmpsByUserAndAnnouncement(anmpUsid, anmpAnmid);
    }

    // 조회- 유저아이디와 기간을 입력받아 참여한 공지 찾기
    public List<Anmp> getUserAnnouncements(String userId, Date startDate, Date endDate) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        List<USer> user = userRepository.findUserById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User ID not found");
        }

        return anmpRepository.findAnmpsByUserAndDateRange(user.get(0).getUsId(), startDate, endDate);
    }

    // 조회- 유저아이디와 월을 입력받아 해당하는 담은 공지 찾기
    public List<Anmp> getAnnouncementsByUserIdAndMonth(String userId, int month) {
        List<USer> user = userRepository.findUserById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User ID not found");
        }

        return anmpRepository.findAnnouncementsByUserIdAndMonth(user.get(0).getUsId(), month);
    }

    // 추가적인 서비스 메소드들을 여기에 구현할 수 있습니다.
}

