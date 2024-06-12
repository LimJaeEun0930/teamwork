package capweb.capprac.service;

import capweb.capprac.entity.Tour;
import capweb.capprac.entity.Tourp;
import capweb.capprac.entity.USer;
import capweb.capprac.repository.TourRepository;
import capweb.capprac.repository.TourpRepository;
import capweb.capprac.repository.USerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class TourpService {

   @Autowired
   private USerRepository userRepository;
   @Autowired
   private TourRepository tourRepository;
    @Autowired
    private TourpRepository tourpRepository;


    // 만들기 - 새로운 Tourp 생성 및 저장
    //유저와 견학을 필수로 입력받게 하고 검색해서 없을때만 견학참여자를 만들어주기
    //현재인원에따라 참가하게 하기   테스트필요
    @Transactional
    public Tourp createTourp(USer tourpUsid, Tour tourpTourid) {
        if (tourpUsid == null || tourpTourid == null) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }

        // 현재 견학의 참여 인원수를 체크
        long currentParticipants = tourpRepository.findTourpsCurrentParticipants(tourpTourid);

        // 견학의 제한 인원수를 가져옴
        int maxParticipants = tourpTourid.getTourRecruitm();

        // 현재 참여 인원수가 제한 인원수를 넘지 않는 경우에만 새로운 참여자를 추가
        if (currentParticipants < maxParticipants) {
            List<Tourp> existTourps = tourpRepository.findTourpsByUserAndTour(tourpUsid, tourpTourid);
            if (existTourps.isEmpty()) {
                Tourp tourp = new Tourp();
                tourp.setTourpUsid(tourpUsid);
                tourp.setTourpTourid(tourpTourid);
                tourpRepository.save(tourp);
                return tourp;
            } else {
                throw new IllegalStateException("사용자는 이미 해당 견학을 담았습니다.");
            }
        } else {
            throw new IllegalStateException("견학의 참여 인원수가 이미 최대입니다.");
        }
    }
    // 삭제 - tourpIndex로 Tourp 삭제
    @Transactional
    public boolean deleteTourp(int tourpIndex) {
       Tourp deltourps = tourpRepository.findTourpByIndex(tourpIndex);
       if(deltourps!=null) {
           tourpRepository.deleteByIndex(tourpIndex);
           return true;
       }
       return false;
    }

    // 조회 - 모든 Tourp 찾기
    public List<Tourp> getAllTourps() {
        return tourpRepository.findAllTourps();
    }

    // 조회 - tourpIndex로 Tourp 찾기
    public Tourp getTourpByIndex(int tourpIndex) {
        return tourpRepository.findTourpByIndex(tourpIndex);
    }

    // 조회 - tourpUsid로 Tourp 찾기
    public List<Tourp> getTourpsByUser(USer tourpUsid) {
        return tourpRepository.findTourpsByUser(tourpUsid);
    }

    // 조회 - tourpTourid로 Tourp 찾기
    public List<Tourp> getTourpsByTour(Tour tourpTourid) {
        return tourpRepository.findTourpsByTour(tourpTourid);
    }

    // 조회 - tourpUsid와 tourpTourid 조합으로 Tourp 찾기
    public List<Tourp> getTourpsByUserAndTour(USer tourpUsid, Tour tourpTourid) {
        return tourpRepository.findTourpsByUserAndTour(tourpUsid, tourpTourid);
    }
    //아이디와 월을 입력받아 아이디를 체크하고 해당하는 견학 찾기
    public List<Tourp> getTourpsByUserIdAndMonth(String userId, int month) {
        List<USer>existusers=userRepository.findUserById(userId);
        if(existusers.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        List<Tourp>findtourps=tourpRepository.findTourpsByUserIdAndMonth(userId, month);
        if(findtourps.isEmpty()) {
            throw new IllegalArgumentException("tourp not found");
        }
        else {
            return findtourps;
        }
    }
}

    // 추가적인 서비스 메소드들을 여기에 구현할 수 있습니다.
