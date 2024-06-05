package capweb.capprac;

import capweb.capprac.entity.*;
import capweb.capprac.repository.*;
import capweb.capprac.service.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//DB반영되는 메소드 쓸때는 트랜잭션걸고 디비에 반영하려면 롤백취소

//ID로 인덱스번호 찾을떄: findById("ID").getindex();

//부테이블에서 외래키는 객체로 들어가기 때문에 거기서 특정 필드가 필요할땐 get함수로 접근

@SpringBootTest
class CappracApplicationTests {
	@Autowired
	private EntityManager entityManager;

	@Autowired UserRepository userRepository;
	@Autowired
	MeetingRoomRepository meetingRoomRepository;
	@Autowired
	CompanyRepository companyRepository;
	@Autowired MrpRepository mrpRepository;
	@Autowired MessageRepository messageRepository;
	@Autowired PlanRepository planRepository;
	@Autowired
	AnnouncementRepository announcementRepository;
	@Autowired
	AnmpRepository anmpRepository;
	@Autowired TourRepository tourRepository;
	@Autowired TourpRepository tourpRepository;
	@Autowired
	LogRepository logRepository;
	@Test
	@Transactional
	@Rollback(false)
		//모든 테이블 값 하나씩 넣어주기
	void contextLoads() {
		System.out.println("test init");
		/*유저*/
		User user = new User();
		user.setUsId("hsj1128");
		user.setUsPw("1234");
		user.setUsName("한승준");
		Date joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
		user.setUsJoindate(joindate);
		user.setUsJoinIP("127.0.0.1");
		user.printfield();
		userRepository.save(user);
		/*미팅룸*/
		MeetingRoom meetingRoom = new MeetingRoom();
		meetingRoom.setMrMrid("firstmr");
		meetingRoom.setMrName("첫방");
		meetingRoom.setMrCategory("공부");
		meetingRoomRepository.save(meetingRoom);
		/*컴퍼니*/
		Company company = new Company();
		company.setCpId("company1");
		company.setCpPw("company1비번");
		company.setCpName("company1이름");
		company.setCpAddr("company1주소");
		company.setCpCategory("company1카테고리");
		company.setCpMtid("company1mento1");
		company.setCpMtname("company1mento1이름");
		joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
		company.setCpJoindate(joindate);
		company.setCpJoinIP("127.0.0.1");
		companyRepository.save(company);
		/*Mrp*/
//		User user = new User();
//		user.setUsId("hsj1128");
//		user.setUsPw("1234");
//		user.setUsName("한승준");
//		Date joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		user.setUsJoindate(joindate);
//		user.setUsJoinIP("127.0.0.1");
//		user.printfield();
//		userRepository.save(user);
//
//
//		MeetingRoom meetingRoom = new MeetingRoom();
//		meetingRoom.setMrMrid("firstmr");
//		meetingRoom.setMrName("첫방");
//		meetingRoom.setMrCategory("공부");
//		meetingRoomRepository.save(meetingRoom);
//
//		Company company = new Company();
//		company.setCpId("company1");
//		company.setCpPw("company1비번");
//		company.setCpName("company1이름");
//		company.setCpAddr("company1주소");
//		company.setCpCategory("company1카테고리");
//		company.setCpMtid("company1mento1");
//		company.setCpMtname("company1mento1이름");
//		joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		company.setCpJoindate(joindate);
//		company.setCpJoinIP("127.0.0.1");
//		companyRepository.save(company);
//
		Mrp mrp = new Mrp();
		//mrp.setMrpUsid(user);
		mrp.setMrpMtid(company);
		mrp.setMrpMrid((meetingRoom));
		mrpRepository.save(mrp);
		/*Msg*/
//		User user = new User();
//		user.setUsId("hsj1128");
//		user.setUsPw("1234");
//		user.setUsName("한승준");
//		Date joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		user.setUsJoindate(joindate);
//		user.setUsJoinIP("127.0.0.1");
//		user.printfield();
//		userRepository.save(user);
//
//
//		MeetingRoom meetingRoom = new MeetingRoom();
//		meetingRoom.setMrMrid("firstmr");
//		meetingRoom.setMrName("첫방");
//		meetingRoom.setMrCategory("공부");
//		meetingRoomRepository.save(meetingRoom);
//
//		Company company = new Company();
//		company.setCpId("company1");
//		company.setCpPw("company1비번");
//		company.setCpName("company1이름");
//		company.setCpAddr("company1주소");
//		company.setCpCategory("company1카테고리");
//		company.setCpMtid("company1mento1");
//		company.setCpMtname("company1mento1이름");
//		joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		company.setCpJoindate(joindate);
//		company.setCpJoinIP("127.0.0.1");
//		companyRepository.save(company);
//
//		Mrp mrp = new Mrp();
//		//mrp.setMrpUsid(user);
//		mrp.setMrpMtid(company);
//		mrp.setMrpMrid((meetingRoom));
//		mrpRepository.save(mrp);
//
		Message message = new Message();
		message.setMsgContent("안녕");
		joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
		message.setMsgTime(joindate);
		message.setMsgSenderusid(mrp);
		message.setMsgMrid(meetingRoom);
		messageRepository.save(message);
		/*Plan*/
//		User user = new User();
//		user.setUsId("hsj1128");
//		user.setUsPw("1234");
//		user.setUsName("한승준");
//		Date joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		user.setUsJoindate(joindate);
//		user.setUsJoinIP("127.0.0.1");
//		user.printfield();
//		userRepository.save(user);
//
//		Company company = new Company();
//		company.setCpId("company1");
//		company.setCpPw("company1비번");
//		company.setCpName("company1이름");
//		company.setCpAddr("company1주소");
//		company.setCpCategory("company1카테고리");
//		company.setCpMtid("company1mento1");
//		company.setCpMtname("company1mento1이름");
//		joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		company.setCpJoindate(joindate);
//		company.setCpJoinIP("127.0.0.1");
//		companyRepository.save(company);
//
		joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
		Plan plan = new Plan();
		plan.setPlanId(joindate);
		plan.setPlanName("plan1");
//		plan.setPlanOpt(1);
//		plan.setPlanUsid(user);
		plan.setPlanOpt(2);
		plan.setPlanCpid(company);
		planRepository.save(plan);
		/*Announcement*/
//		Company company = new Company();
//		company.setCpId("company1");
//		company.setCpPw("company1비번");
//		company.setCpName("company1이름");
//		company.setCpAddr("company1주소");
//		company.setCpCategory("company1카테고리");
//		company.setCpMtid("company1mento1");
//		company.setCpMtname("company1mento1이름");
//		Date joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		company.setCpJoindate(joindate);
//		company.setCpJoinIP("127.0.0.1");
//		companyRepository.save(company);
//
		Announcement announcement = new Announcement();
		announcement.setAnmName("announcement1");
		announcement.setAnmStartDate(joindate);
		announcement.setAnmEndDate(joindate);
		announcement.setAnmEmptype("announcement1");
		announcement.setAnmRecruitm(1);
		announcement.setAnmCpid(company);
		announcementRepository.save(announcement);
		/*Anmp*/
//		User user = new User();
//		user.setUsId("hsj1128");
//		user.setUsPw("1234");
//		user.setUsName("한승준");
//		Date joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		user.setUsJoindate(joindate);
//		user.setUsJoinIP("127.0.0.1");
//		user.printfield();
//		userRepository.save(user);
//
//		Company company = new Company();
//		company.setCpId("company1");
//		company.setCpPw("company1비번");
//		company.setCpName("company1이름");
//		company.setCpAddr("company1주소");
//		company.setCpCategory("company1카테고리");
//		company.setCpMtid("company1mento1");
//		company.setCpMtname("company1mento1이름");
//		joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		company.setCpJoindate(joindate);
//		company.setCpJoinIP("127.0.0.1");
//		companyRepository.save(company);
//
//		Announcement announcement = new Announcement();
//		announcement.setAnmName("announcement1");
//		announcement.setAnmStartDate(joindate);
//		announcement.setAnmEndDate(joindate);
//		announcement.setAnmEmptype("announcement1");
//		announcement.setAnmRecruitm(1);
//		announcement.setAnmCpid(company);
//		announcementRepository.save(announcement);
//
		Anmp anmp = new Anmp();
		anmp.setAnmpUsid(user);
		anmp.setAnmpAnmid(announcement);
		anmpRepository.save(anmp);
		/*Tour*/
//		Company company = new Company();
//		company.setCpId("company1");
//		company.setCpPw("company1비번");
//		company.setCpName("company1이름");
//		company.setCpAddr("company1주소");
//		company.setCpCategory("company1카테고리");
//		company.setCpMtid("company1mento1");
//		company.setCpMtname("company1mento1이름");
//		Date joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		company.setCpJoindate(joindate);
//		company.setCpJoinIP("127.0.0.1");
//		companyRepository.save(company);
//
		joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
		Tour tour = new Tour();
		tour.setTourDay(joindate);
		tour.setTourName("tour1");
		tour.setTourRecruitm(10);
		tour.setTourCpid(company);
		tourRepository.save(tour);
		/*Tourp*/
//		User user = new User();
//		user.setUsId("hsj1128");
//		user.setUsPw("1234");
//		user.setUsName("한승준");
//		Date joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		user.setUsJoindate(joindate);
//		user.setUsJoinIP("127.0.0.1");
//		user.printfield();
//		userRepository.save(user);
//
//		Company company = new Company();
//		company.setCpId("company1");
//		company.setCpPw("company1비번");
//		company.setCpName("company1이름");
//		company.setCpAddr("company1주소");
//		company.setCpCategory("company1카테고리");
//		company.setCpMtid("company1mento1");
//		company.setCpMtname("company1mento1이름");
//		joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		company.setCpJoindate(joindate);
//		company.setCpJoinIP("127.0.0.1");
//		companyRepository.save(company);
//
//		joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		Tour tour = new Tour();
//		tour.setTourDay(joindate);
//		tour.setTourName("tour1");
//		tour.setTourRecruitm(10);
//		tour.setTourCpid(company);
//		tourRepository.save(tour);
//
		Tourp tourp = new Tourp();
		tourp.setTourpUsid(user);
		tourp.setTourpTourid(tour);
		tourpRepository.save(tourp);
		/*Log*/
//		User user = new User();
//		user.setUsId("hsj1128");
//		user.setUsPw("1234");
//		user.setUsName("한승준");
//		Date joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		user.setUsJoindate(joindate);
//		user.setUsJoinIP("127.0.0.1");
//		user.printfield();
//		userRepository.save(user);
//
//		Company company = new Company();
//		company.setCpId("company1");
//		company.setCpPw("company1비번");
//		company.setCpName("company1이름");
//		company.setCpAddr("company1주소");
//		company.setCpCategory("company1카테고리");
//		company.setCpMtid("company1mento1");
//		company.setCpMtname("company1mento1이름");
//		joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
//		company.setCpJoindate(joindate);
//		company.setCpJoinIP("127.0.0.1");
//		companyRepository.save(company);
//
		joindate =  new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime();
		Log log = new Log();
		log.setLogEnterdate(joindate);
		log.setLogEnterIP("127.0.0.1");
//		log.setLogOpt(1);
//		log.setLogUsid(user);
		log.setLogOpt(2);
		log.setLogCpid(company);
		logRepository.save(log);



	}
	@Test
	@Transactional
	void whenSaveUser_thenUserIsSaved() {
		User user = new User();
		user.setUsId("testId");
		user.setUsPw("testPw");
		user.setUsName("testName");
		user.setUsJoindate(new Date());
		user.setUsJoinIP("127.0.0.1");

		userRepository.save(user);

		User foundUser = entityManager.find(User.class, user.getUsIndex());
		assertNotNull(foundUser);
		assertEquals("testId", foundUser.getUsId());
	}
	@Test
	@Transactional
	void whenFindUserByIndex_thenCorrectUserIsReturned() {
		User user = new User();
		user.setUsId("testId");
		user.setUsPw("testPw");
		user.setUsName("testName");
		user.setUsJoindate(new Date());
		user.setUsJoinIP("127.0.0.1");
		entityManager.persist(user);

		User foundUser = userRepository.findUserByIndex(user.getUsIndex());
		assertNotNull(foundUser);
		assertEquals("testId", foundUser.getUsId());
	}

	@Test
	@Transactional
	void whenFindAllUsers_thenAllUsersAreReturned() {
		User user1 = new User();
		user1.setUsId("testId1");
		user1.setUsPw("testPw");
		user1.setUsName("testName");
		user1.setUsJoindate(new Date());
		user1.setUsJoinIP("127.0.0.1");
		entityManager.persist(user1);

		User user2 = new User();
		user2.setUsId("testId2");
		user2.setUsPw("testPw");
		user2.setUsName("testName");
		user2.setUsJoindate(new Date());
		user2.setUsJoinIP("127.0.0.1");
		entityManager.persist(user2);

		List<User> users = userRepository.findAllUsers();
		assertNotNull(users);
		assertTrue(users.size() >= 2);
	}
	@Test
	@Transactional
	void whenUpdateUser_thenUserIsUpdated() {
		User user = new User();
		user.setUsId("testId");
		user.setUsPw("testPw");
		user.setUsName("testName");
		user.setUsJoindate(new Date());
		user.setUsJoinIP("127.0.0.1");
		entityManager.persist(user);

		user.setUsPw("newPw");
		userRepository.update(user);

		User updatedUser = entityManager.find(User.class, user.getUsIndex());
		assertEquals("newPw", updatedUser.getUsPw());
	}
	@Test
	@Transactional
	void whenDeleteUserByIndex_thenUserIsDeleted() {
		User user = new User();
		user.setUsId("testId");
		user.setUsPw("testPw");
		user.setUsName("testName");
		user.setUsJoindate(new Date());
		user.setUsJoinIP("127.0.0.1");
		entityManager.persist(user);

		userRepository.deleteByIndex(user.getUsIndex());

		User deletedUser = entityManager.find(User.class, user.getUsIndex());
		assertNull(deletedUser);
	}
	@Test
	@Transactional
	void whenFindUserByNonExistingId_thenExceptionIsThrown() {
		User user = new User();
		user.setUsId("testId");
		user.setUsPw("testPw");
		user.setUsName("testName");
		user.setUsJoindate(new Date());
		user.setUsJoinIP("127.0.0.1");
		entityManager.persist(user);
		// 존재하지 않는 ID로 사용자를 찾을 때 예외가 발생하는지 검증
		assertThrows(EmptyResultDataAccessException.class, () -> {
			userRepository.findUserById("nonExistingId");
		});
	}

	//위에거로 수정
//	@Test
//	@Transactional
//	void whenFindUserById_thenCorrectUserIsReturned() {
//		User user = new User();
//		user.setUsId("testId");
//		user.setUsPw("testPw");
//		user.setUsName("testName");
//		user.setUsJoindate(new Date());
//		user.setUsJoinIP("127.0.0.1");
//		entityManager.persist(user);
//
//		User foundUser = userRepository.findUserById("uniqueId");
//		assertNotNull(foundUser);
//		assertEquals("uniqueId", foundUser.getUsId());
//	}

	@Test
	@Transactional
	void whenFindUsersByName_thenCorrectUsersAreReturned() {
		User user1 = new User();
		user1.setUsId("testId");
		user1.setUsPw("testPw");
		user1.setUsName("testName");
		user1.setUsJoindate(new Date());
		user1.setUsJoinIP("127.0.0.1");
		entityManager.persist(user1);

		User user2 = new User();
		user2.setUsId("testId2");
		user2.setUsPw("testPw");
		user2.setUsName("testName");
		user2.setUsJoindate(new Date());
		user2.setUsJoinIP("127.0.0.1");
		entityManager.persist(user2);

		List<User> users = userRepository.findUsersByName("testName");
		assertNotNull(users);
		assertTrue(users.size() >= 2);
	}
	@Test
	@Transactional
	void whenFindUsersByJoindate_thenCorrectUsersAreReturned() {
		Date joinDate = new Date();
		User user1 = new User();
		user1.setUsId("testId");
		user1.setUsPw("testPw");
		user1.setUsName("testName");
		user1.setUsJoindate(joinDate);
		user1.setUsJoinIP("127.0.0.1");
		entityManager.persist(user1);


		User user2 = new User();
		user2.setUsId("testId2");
		user2.setUsPw("testPw");
		user2.setUsName("testName");
		user2.setUsJoindate(joinDate);
		user2.setUsJoinIP("127.0.0.1");
		entityManager.persist(user2);

		List<User> users = userRepository.findUsersByJoindate(joinDate);
		assertNotNull(users);
		assertTrue(users.size() >= 2);
	}
	/*------------------------------유저레포지토리 끝-------------------*/
}
