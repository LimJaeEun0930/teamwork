package capweb.capprac.service;

import capweb.capprac.entity.Company;
import capweb.capprac.entity.Tour;
import capweb.capprac.repository.CompanyRepository;
import capweb.capprac.repository.TourRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TourServiceTest {

    @Autowired
    private TourService tourService;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Company company;
    private Tour tour;
    private  Date date;

    @BeforeEach
    void setUp() {
        // 초기화 코드가 필요하다면 여기에 작성합니다.
        date = new Date();
        company = new Company();
        company.setCpId("cpid");
        company.setCpPw("cppw");
        company.setCpName("cpname");
        company.setCpCategory("cpcate");
        company.setCpAddr("cpaddr");
        company.setCpMtid("cpmtid");
        company.setCpMtname("cpmtname");
        companyRepository.save(company);
        tour = new Tour();
        tour.setTourCpid(company);
        tour.setTourDay(date);
        tour.setTourName("tourname");
        tour.setTourRecruitm(1);
        tourRepository.save(tour);
    }

    // Test for createTour method
    @Test
    public void testCreateTour() {
        Date tourDay = date;
        String tourName = "New Tour";
        int tourRecruitm = 10;

        Company ccompany;
        ccompany = new Company();
        ccompany.setCpId("ccpid");
        ccompany.setCpPw("cppw");
        ccompany.setCpName("ccpname");
        ccompany.setCpCategory("cpcate");
        ccompany.setCpAddr("ccpaddr");
        ccompany.setCpMtid("ccpmtid");
        ccompany.setCpMtname("ccpmtname");
        companyRepository.save(ccompany);
        //Tour newTour = tourService.createTour(tourDay, tourName, tourRecruitm,company);
        Tour newTour = tourService.createTour(tourDay, tourName, tourRecruitm,ccompany);
        assertThat(newTour).isNotNull();
        assertThat(newTour.getTourDay()).isEqualTo(tourDay);
        assertThat(newTour.getTourName()).isEqualTo(tourName);
        assertThat(newTour.getTourRecruitm()).isEqualTo(tourRecruitm);
        assertThat(newTour.getTourCpid()).isEqualTo(ccompany);
    }

    // Additional tests for other methods can be structured similarly
    // ...

    // Test for updateTour method
    @Test
    public void testUpdateTour() {
        // Assume that a tour with index 1 exists
        int tourIndex = 1;
        Date tourDay = new Date();
        String tourName = "Updated Tour";
        int tourRecruitm = 20;

        boolean result = tourService.updateTour(tourIndex, tourDay, tourName, tourRecruitm,company);

        assertThat(result).isTrue();
        Tour updatedTour = tourService.getTourByIndex(tourIndex);
        assertThat(updatedTour).isNotNull();
        //assertThat(updatedTour.getTourDay()).isEqualTo(tourDay);
        //assertThat(updatedTour.getTourName()).isEqualTo(tourName);
        //assertThat(updatedTour.getTourRecruitm()).isEqualTo(tourRecruitm);
    }

    // Test for deleteTour method
    @Test
    public void testDeleteTour() {
        // Assume that a tour with index 1 exists
        int tourIndex = 1;

        boolean result = tourService.deleteTour(tourIndex);

        assertThat(result).isTrue();
        //assertThrows(IllegalArgumentException.class, () -> tourService.getTourByIndex(tourIndex));
    }

    // Test for getAllTours method
    @Test
    public void testGetAllTours() {
        List<Tour> tours = tourService.getAllTours();

        assertThat(tours).isNotNull();
        assertThat(tours.size()).isGreaterThan(0);
    }

    // Test for getTourByIndex method
    @Test
    public void testGetTourByIndex() {
        // Assume that a tour with index 1 exists
        int tourIndex = 1;

        Tour tour = tourService.getTourByIndex(tourIndex);

        assertThat(tour).isNotNull();
        assertThat(tour.getTourIndex()).isEqualTo(tourIndex);
    }

    // Test for getToursByDay method
    @Test
    public void testGetToursByDay() {
        Date tourDay = date;

        List<Tour> tours = tourService.getToursByDay(tourDay);

        assertThat(tours).isNotEmpty();
        //assertThat(tours.get(0).getTourDay()).isEqualTo(tourDay);
    }

    // Test for getToursByName method
    @Test
    public void testGetToursByName() {
        String tourName = "tourname";

        List<Tour> tours = tourService.getToursByName(tourName);

        assertThat(tours).isNotEmpty();
        assertThat(tours.get(0).getTourName()).isEqualTo(tourName);
    }

    // Test for getToursByRecruitm method
    @Test
    public void testGetToursByRecruitm() {
        int tourRecruitm = 1;

        List<Tour> tours = tourService.getToursByRecruitm(tourRecruitm);

        assertThat(tours).isNotEmpty();
        assertThat(tours.get(0).getTourRecruitm()).isEqualTo(tourRecruitm);
    }

    // Test for getToursByCompany method
    @Test
    public void testGetToursByCompany() {

        List<Tour> tours = tourService.getToursByCompany(company);

        assertThat(tours).isNotEmpty();
        //assertThat(tours.get(0).getTourCpid()).isEqualTo(company);
    }

    // Test for getToursByDayAndCompany method
    @Test
    public void testGetToursByDayAndCompany() {
        Date tourDay = date;

        List<Tour> tours = tourService.getToursByDayAndCompany(tourDay,company);

        assertThat(tours).isNotEmpty();
        //assertThat(tours.get(0).getTourDay()).isEqualTo(tourDay);
        //assertThat(tours.get(0).getTourCpid()).isEqualTo(company);
    }
}
