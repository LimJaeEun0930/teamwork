package capweb.capprac;

import java.util.Date;
import java.util.List;

public class TourService {

    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    // 만들기 - 새로운 Tour 생성 및 저장
    public Tour createTour(Date tourDay, String tourName, int tourRecruitm, Company tourCpid) {
        if (tourDay == null || tourName == null || tourCpid == null) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
        Tour tour = new Tour();
        tour.setTourDay(tourDay);
        tour.setTourName(tourName);
        tour.setTourRecruitm(tourRecruitm);
        tour.setTourCpid(tourCpid);
        tourRepository.save(tour);
        return tour;
    }

    // 수정 - Tour 업데이트 (tourDay, tourName, tourRecruitm만 수정 가능)
    public void updateTour(int tourIndex, Date tourDay, String tourName, int tourRecruitm) {
        Tour tour = tourRepository.findTourByIndex(tourIndex);
        if (tour != null) {
            tour.setTourDay(tourDay);
            tour.setTourName(tourName);
            tour.setTourRecruitm(tourRecruitm);
            // tourCpid는 수정하지 않음
            tourRepository.update(tour);
        }
    }

    // 삭제 - tourIndex로 Tour 삭제
    public void deleteTour(int tourIndex) {
        tourRepository.deleteByIndex(tourIndex);
    }

    // 조회 - 모든 Tour 찾기
    public List<Tour> getAllTours() {
        return tourRepository.findAllTours();
    }

    // 조회 - tourIndex로 Tour 찾기
    public Tour getTourByIndex(int tourIndex) {
        return tourRepository.findTourByIndex(tourIndex);
    }

    // 조회 - tourDay로 Tour 찾기
    public List<Tour> getToursByDay(Date tourDay) {
        return tourRepository.findToursByDay(tourDay);
    }

    // 조회 - tourName으로 Tour 찾기
    public List<Tour> getToursByName(String tourName) {
        return tourRepository.findToursByName(tourName);
    }

    // 조회 - tourRecruitm으로 Tour 찾기
    public List<Tour> getToursByRecruitm(int tourRecruitm) {
        return tourRepository.findToursByRecruitm(tourRecruitm);
    }

    // 조회 - tourCpid로 Tour 찾기
    public List<Tour> getToursByCompany(Company tourCpid) {
        return tourRepository.findToursByCompany(tourCpid);
    }

    // 조회 - tourDay와 tourCpid로 Tour 찾기
    public List<Tour> getToursByDayAndCompany(Date tourDay, Company tourCpid) {
        return tourRepository.findToursByDayAndCompany(tourDay, tourCpid);
    }

    // 추가적인 서비스 메소드들을 여기에 구현할 수 있습니다.
}
