package capweb.capprac;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class MrpService {

    private final MrpRepository mrpRepository;

    public MrpService(MrpRepository mrpRepository) {
        this.mrpRepository = mrpRepository;
    }

    // 만들기 - 새로운 Mrp 생성 및 저장
    public Mrp createMrp(User user, MeetingRoom meetingRoom, Company mentor) {
        Mrp mrp = new Mrp();

        // User와 Company가 null이 아닐 때만 설정
        Optional.ofNullable(user).ifPresent(mrp::setMrpUsid);
        Optional.ofNullable(meetingRoom).ifPresent(mrp::setMrpMrid);
        Optional.ofNullable(mentor).ifPresent(mrp::setMrpMtid);

        mrpRepository.save(mrp);
        return mrp;
    }


    // 삭제 - mrpIndex로 Mrp 삭제
    public boolean deleteMrp(int mrpIndex) {
        Mrp mrp = mrpRepository.findByIndex(mrpIndex);
        if (mrp != null) {
            mrpRepository.deleteByIndex(mrpIndex);
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
