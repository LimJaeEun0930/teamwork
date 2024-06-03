package capweb.capprac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class TourpService {

   @Autowired
   private UserRepository userRepository;
   @Autowired
   private  TourRepository tourRepository;
    @Autowired
    private TourpRepository tourpRepository;


    // 만들기 - 새로운 Tourp 생성 및 저장
    @Transactional
    public Tourp createTourp(User tourpUsid, Tour tourpTourid) {
        if (tourpUsid == null || tourpTourid == null) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
        List<Tourp>existtourps = tourpRepository.findTourpsByUserAndTour(tourpUsid,tourpTourid);
        if(existtourps.isEmpty()) {
            Tourp tourp = new Tourp();
            tourp.setTourpUsid(tourpUsid);
            tourp.setTourpTourid(tourpTourid);
            tourpRepository.save(tourp);
            return tourp;
        }
        else{
            throw new IllegalStateException("사용자는 이미 해당 견학을 담았습니다.");
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
    public List<Tourp> getTourpsByUser(User tourpUsid) {
        return tourpRepository.findTourpsByUser(tourpUsid);
    }

    // 조회 - tourpTourid로 Tourp 찾기
    public List<Tourp> getTourpsByTour(Tour tourpTourid) {
        return tourpRepository.findTourpsByTour(tourpTourid);
    }

    // 조회 - tourpUsid와 tourpTourid 조합으로 Tourp 찾기
    public List<Tourp> getTourpsByUserAndTour(User tourpUsid, Tour tourpTourid) {
        return tourpRepository.findTourpsByUserAndTour(tourpUsid, tourpTourid);
    }

    // 추가적인 서비스 메소드들을 여기에 구현할 수 있습니다.
}
