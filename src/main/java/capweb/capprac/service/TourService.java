package capweb.capprac.service;

import capweb.capprac.entity.Company;
import capweb.capprac.entity.Tour;
import capweb.capprac.repository.CompanyRepository;
import capweb.capprac.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
public class TourService {

   @Autowired
   private TourRepository tourRepository;
    @Autowired
    private CompanyRepository companyRepository;

    // 만들기 - 새로운 Tour 생성 및 저장
    //회사는 같은 날짜에 견학 하나
    //견학날짜,견학명,제한인원,회사아이디를 필수로 입력받게하고 견학날짜와 견학아이디를 통해 검새해서 없을때만 견학 만들어주기
    @Transactional
    public Tour createTour(Date tourDay, String tourName, int tourRecruitm, Company tourCpid) {
        if (tourDay == null || tourName == null || tourName.trim().isEmpty() || tourRecruitm==0 || tourCpid == null) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
        List<Tour> existtours = tourRepository.findToursByDayAndCompany(tourDay,tourCpid);
        if(existtours.isEmpty()) {
            Tour tour = new Tour();
            tour.setTourDay(tourDay);
            tour.setTourName(tourName);
            tour.setTourRecruitm(tourRecruitm);
            tour.setTourCpid(tourCpid);
            tourRepository.save(tour);
            return tour;
        }
        else{
            throw new IllegalStateException("해당날짜에 동일업체 견학 존재");
        }
    }
    // 수정 - Tour 업데이트 (tourDay, tourName, tourRecruitm만 수정 가능)
    //인덱스,견학날짜,제한인원,회사아이디를 필수로 입력받고 마찬가지로 날짜와 회사아이디를 입력받아서 없으면 입력받은 인덱스에 해당하는 견학 수정
    @Transactional
    public boolean updateTour(int tourIndex, Date tourDay, String tourName, int tourRecruitm,Company tourCpid) {
        if (tourDay == null || tourName == null || tourName.trim().isEmpty() || tourRecruitm==0 || tourCpid == null) {
            return false;
            //throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
        List<Tour> existtours = tourRepository.findToursByDayAndCompany(tourDay,tourCpid);
        if(existtours.isEmpty()) {
            Tour tour = tourRepository.findTourByIndex(tourIndex);
            if (tour != null) {
                tour.setTourDay(tourDay);
                tour.setTourName(tourName);
                tour.setTourRecruitm(tourRecruitm);
                // tourCpid는 수정하지 않음
                tourRepository.update(tour);
                return  true;
            }
            return  false;
            //인덱스번호
        }
        return  false;
    }
    // 삭제 - tourIndex로 Tour 삭제
    @Transactional
    public boolean deleteTour(int tourIndex) {
        Tour tour = tourRepository.findTourByIndex(tourIndex);
        if(tour!=null) {
            tourRepository.deleteByIndex(tourIndex);
            return true;
        }
        return false;
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
    //회사아이디와 월을 입력받아 체크하고 해당하는 전체 견학들을 찾기 테스트필요
    public List<Tour> getToursByCompanyIdAndMonth(String companyId, int month) {
        List<Company>findcompanies=companyRepository.findCompanyById(companyId);
        if(findcompanies.isEmpty()) {
            throw new IllegalArgumentException("company not found");
        }
        List<Tour>findtours=tourRepository.findToursByCompanyIdAndMonth(companyId, month);
        if(findtours.isEmpty()){
            throw new IllegalStateException("tours not found");
        }
        else{
            return findtours;
        }
    }

    // 추가적인 서비스 메소드들을 여기에 구현할 수 있습니다.
}
