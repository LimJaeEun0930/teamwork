package capweb.capprac;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TourRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Company company;
    private  Tour tour;
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
        company.setCpJoindate(date);
        company.setCpJoinIP("127.00.1");
        companyRepository.save(company);
        tour = new Tour();
        tour.setTourCpid(company);
        tour.setTourDay(date);
        tour.setTourName("tourname");
        tour.setTourRecruitm(1);
        tourRepository.save(tour);
    }

    @Test
    void whenSaveTour_thenTourIsSaved() {
        Tour foundTour = entityManager.find(Tour.class, tour.getTourIndex());
        assertNotNull(foundTour);
        // 필드 값 검증
    }

    @Test
    @Transactional
    void whenFindTourByIndex_thenCorrectTourIsReturned() {
        Tour foundTour = tourRepository.findTourByIndex(tour.getTourIndex());
        assertNotNull(foundTour);
        // 필드 값 검증
    }

    @Test
    @Transactional
    void whenFindAllTours_thenAllToursAreReturned() {
        List<Tour> tours = tourRepository.findAllTours();
        assertNotNull(tours);
    }

    @Test
    @Transactional
    void whenUpdateTour_thenTourIsUpdated() {
        tour.setTourName("updatetourname");
        // 필드 업데이트
        tourRepository.update(tour);
        Tour uptour = tourRepository.findTourByIndex(tour.getTourIndex());
        assertNotNull(uptour);
    }

    @Test
    @Transactional
    void whenDeleteTourByIndex_thenTourIsDeleted() {

        tourRepository.deleteByIndex(tour.getTourIndex());

        Tour deletedTour = tourRepository.findTourByIndex(tour.getTourIndex());
        assertNull(deletedTour);
    }

    @Test
    public void whenFindByDay_thenReturnTours() {
        Date tourDay = date;

        List<Tour> foundTours = tourRepository.findToursByDay(tourDay);
        assertThat(foundTours).isNotEmpty();
        //assertThat(foundTours.get(0).getTourDay()).isEqualTo(tourDay);
    }

    // Test for finding Tours by name
    @Test
    public void whenFindByName_thenReturnTours() {

        List<Tour> foundTours = tourRepository.findToursByName("tourname");

        assertThat(foundTours).isNotEmpty();
        assertThat(foundTours.get(0).getTourName()).isEqualTo("tourname");
    }

    // Test for finding Tours by recruitment number
    @Test
    public void whenFindByRecruitm_thenReturnTours() {
        List<Tour> foundTours = tourRepository.findToursByRecruitm(1);

        assertThat(foundTours).isNotEmpty();
        assertThat(foundTours.get(0).getTourRecruitm()).isEqualTo(1);
    }

    // Test for finding Tours by company
    @Test
    public void whenFindByCompany_thenReturnTours() {
        List<Tour> foundTours = tourRepository.findToursByCompany(company);

        assertThat(foundTours).isNotEmpty();
        //assertThat(foundTours.get(0).getTourCpid()).isEqualTo(company);
    }

    // Test for finding Tours by day and company
    @Test
    public void whenFindByDayAndCompany_thenReturnTours() {
        Date tourDay = date;

        List<Tour> foundTours = tourRepository.findToursByDayAndCompany(tourDay,company);

        assertThat(foundTours).isNotEmpty();
        //assertThat(foundTours.get(0).getTourDay()).isEqualTo(tourDay);
        //assertThat(foundTours.get(0).getTourCpid()).isEqualTo(company);
    }

    // 나머지 메소드들에 대한 테스트 케이스도 비슷한 방식으로 작성할 수 있습니다.
}
