package capweb.capprac;

import java.util.List;

public class AnmpService {

    private final AnmpRepository anmpRepository;

    public AnmpService(AnmpRepository anmpRepository) {
        this.anmpRepository = anmpRepository;
    }

    // 만들기 - 새로운 Anmp 생성 및 저장
    public Anmp createAnmp(User anmpUsid, Announcement anmpAnmid) {
        if (anmpUsid == null || anmpAnmid == null) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
        Anmp anmp = new Anmp();
        anmp.setAnmpUsid(anmpUsid);
        anmp.setAnmpAnmid(anmpAnmid);
        anmpRepository.save(anmp);
        return anmp;
    }

    // 조회 - 모든 Anmp 찾기
    public List<Anmp> getAllAnmps() {
        return anmpRepository.findAllAnmps();
    }

    // 조회 - anmpIndex로 Anmp 찾기
    public Anmp getAnmpByIndex(int anmpIndex) {
        return anmpRepository.findAnmpByIndex(anmpIndex);
    }

    // 조회 - anmpUsid로 Anmp 찾기
    public List<Anmp> getAnmpsByUser(User anmpUsid) {
        return anmpRepository.findAnmpsByUser(anmpUsid);
    }

    // 조회 - anmpAnmid로 Anmp 찾기
    public List<Anmp> getAnmpsByAnnouncement(Announcement anmpAnmid) {
        return anmpRepository.findAnmpsByAnnouncement(anmpAnmid);
    }

    // 조회 - anmpUsid와 anmpAnmid 조합으로 Anmp 찾기
    public List<Anmp> getAnmpsByUserAndAnnouncement(User anmpUsid, Announcement anmpAnmid) {
        return anmpRepository.findAnmpsByUserAndAnnouncement(anmpUsid, anmpAnmid);
    }

    // 삭제 - anmpIndex로 Anmp 삭제
    public void deleteAnmp(int anmpIndex) {
        anmpRepository.deleteByIndex(anmpIndex);
    }

    // 추가적인 서비스 메소드들을 여기에 구현할 수 있습니다.
}
