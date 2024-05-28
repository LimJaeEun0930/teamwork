package capweb.capprac;

import java.util.List;

public class TourpService {

    private final TourpRepository tourpRepository;

    public TourpService(TourpRepository tourpRepository) {
        this.tourpRepository = tourpRepository;
    }

    // 만들기 - 새로운 Tourp 생성 및 저장
    public Tourp createTourp(User tourpUsid, Tour tourpTourid) {
        if (tourpUsid == null || tourpTourid == null) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
        Tourp tourp = new Tourp();
        tourp.setTourpUsid(tourpUsid);
        tourp.setTourpTourid(tourpTourid);
        tourpRepository.save(tourp);
        return tourp;
    }

    // 삭제 - tourpIndex로 Tourp 삭제
    public void deleteTourp(int tourpIndex) {
        tourpRepository.deleteByIndex(tourpIndex);
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
