package capweb.capprac;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MrpService {

    @Autowired
    private  MrpRepository mrpRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  MeetingRoomRepository meetingRoomRepository;
    @Autowired
    private  CompanyRepository companyRepository;


    // 만들기 - 새로운 Mrp 생성 및 저장
    //id로 객체찾아서.get(0)해서 넣어주기
    @Transactional
    public Mrp createMrp(User user, MeetingRoom meetingRoom, Company mentor) {
        if(meetingRoom==null||(user==null&&mentor==null)){
            throw new IllegalArgumentException("만들 정보 부족");
        }
        if(user!=null) {
            List<Mrp> existumrps = mrpRepository.findByUserAndMeetingRoom(user, meetingRoom);
            if (!existumrps.isEmpty()) {
                throw new IllegalStateException("사용자는 이미 이 모임방에 참여하고 있습니다.");
            }
        }
        else{
            List<Mrp> existcmrps = mrpRepository.findByCompanyAndMeetingRoom(mentor, meetingRoom);
            if(!existcmrps.isEmpty()){
                throw new IllegalStateException("멘토는 이미 이 모임방에 참여하고 있습니다.");
            }
        }
        Mrp mrp = new Mrp();

        // User와 Company가 null이 아닐 때만 설정
        Optional.ofNullable(user).ifPresent(mrp::setMrpUsid);
        Optional.ofNullable(meetingRoom).ifPresent(mrp::setMrpMrid);
        Optional.ofNullable(mentor).ifPresent(mrp::setMrpMtid);

        mrpRepository.save(mrp);
        return mrp;
    }


    // 삭제 - mrpIndex로 Mrp 삭제
    @Transactional
    public boolean deleteMrp(int mrpIndex) {
        Mrp mrp = mrpRepository.findByIndex(mrpIndex);
        if (mrp != null) {
            mrpRepository.deleteByIndex(mrpIndex);
            return true; // 삭제 성공
        }
        return false; // 해당 인덱스의 Mrp가 없으면 삭제 실패
    }

    @Transactional
    public boolean deleteMrpProcess(String usid, String mrid) {
        List<User> users = userRepository.findUserById(usid);
        List<MeetingRoom> meetingRooms = meetingRoomRepository.findMeetingRoomByMrid(mrid);

        // 사용자 또는 회의실이 없는 경우 처리
        if (users.isEmpty() || meetingRooms.isEmpty()) {
            return false; // 사용자 또는 회의실이 없으면 삭제 실패
        }

        List<Mrp> mrps = mrpRepository.findByUserAndMeetingRoom(users.get(0), meetingRooms.get(0));

        // mrps 리스트가 비어있는 경우 처리
        if (mrps.isEmpty()) {
            return false; // 해당 사용자와 회의실에 대한 Mrp가 없으면 삭제 실패
        }

        Mrp mrp = mrpRepository.findByIndex(mrps.get(0).getMrpIndex());

        // mrp가 null인 경우는 findByIndex 메소드가 null을 반환할 수 있기 때문에 체크
        if (mrp != null) {
            mrpRepository.deleteByIndex(mrp.getMrpIndex());
            return true; // 삭제 성공
        }

        return false; // 해당 인덱스의 Mrp가 없으면 삭제 실패
    }

    @Transactional
    public boolean cdeleteMrpProcess(String mtid, String mrid) {
        List<Company> companies = companyRepository.findCompanyByMtid(mtid);
        List<MeetingRoom> meetingRooms = meetingRoomRepository.findMeetingRoomByMrid(mrid);

        // 사용자 또는 회의실이 없는 경우 처리
        if (companies.isEmpty() || meetingRooms.isEmpty()) {
            return false; // 사용자 또는 회의실이 없으면 삭제 실패
        }

        List<Mrp> mrps = mrpRepository.findByCompanyAndMeetingRoom(companies.get(0), meetingRooms.get(0));

        // mrps 리스트가 비어있는 경우 처리
        if (mrps.isEmpty()) {
            return false; // 해당 사용자와 회의실에 대한 Mrp가 없으면 삭제 실패
        }

        Mrp mrp = mrpRepository.findByIndex(mrps.get(0).getMrpIndex());

        // mrp가 null인 경우는 findByIndex 메소드가 null을 반환할 수 있기 때문에 체크
        if (mrp != null) {
            mrpRepository.deleteByIndex(mrp.getMrpIndex());
            return true; // 삭제 성공
        }

        return false; // 해당 인덱스의 Mrp가 없으면 삭제 실패
    }


    // 조회 - 모든 Mrp 찾기
    public List<Mrp> findAllMrps() {
        return mrpRepository.findAll();
    }

    // 조회 - 특정 사용자가 속한 모든 Mrp 찾기
    public List<Mrp> findMrpsByUser(User user) {
        return mrpRepository.findByUser(user);
    }

    // 조회 - 특정 모임방에 속한 모든 Mrp 찾기
    public List<Mrp> findMrpsByMeetingRoom(MeetingRoom meetingRoom) {
        return mrpRepository.findByMeetingRoom(meetingRoom);
    }

    // 조회 - 특정 멘토가 속한 모든 Mrp 찾기
    public List<Mrp> findMrpsByCompany(Company company) {
        return mrpRepository.findByCompany(company);
    }

    // 조회 - 특정 사용자와 모임방에 동시에 속한 Mrp 찾기
    public List<Mrp> findMrpsByUserAndMeetingRoom(User user, MeetingRoom meetingRoom) {
        return mrpRepository.findByUserAndMeetingRoom(user, meetingRoom);
    }
    // 조회 - 특정 멘토와 모임방에 동시에 속한 Mrp 찾기
    public List<Mrp> findMrpsByCompanyAndMeetingRoom(Company company, MeetingRoom meetingRoom) {
        return mrpRepository.findByCompanyAndMeetingRoom(company, meetingRoom);
    }
    // MrpService 클래스 내에 모임방에 속한 유저와 멘토의 총 수를 반환하는 메소드 추가
    public int countParticipantsInMeetingRoom(MeetingRoom meetingRoom) {
        // 유저 수 조회
        int userCount = mrpRepository.findByMeetingRoom(meetingRoom).size();
        // 멘토 수 조회
        int mentorCount = mrpRepository.findByMeetingRoomAndMentor(meetingRoom).size();
        // 총 참가자 수 반환
        return userCount + mentorCount;
    }
    // 추가적인 서비스 메소드들...
}
