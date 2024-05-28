package capweb.capprac;

import java.util.Date;
import java.util.List;

public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    // 만들기 - 새로운 Announcement 생성 및 저장
    public Announcement createAnnouncement(String anmName, Date anmStartDate, Date anmEndDate, String anmEmptype, int anmRecruitm, Company anmCpid) {
        if (anmName == null || anmName.trim().isEmpty() || anmStartDate == null || anmEndDate == null || anmEmptype == null || anmEmptype.trim().isEmpty() || anmRecruitm==0 || anmCpid == null) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
        Announcement announcement = new Announcement();
        announcement.setAnmName(anmName);
        announcement.setAnmStartDate(anmStartDate);
        announcement.setAnmEndDate(anmEndDate);
        announcement.setAnmEmptype(anmEmptype);
        announcement.setAnmRecruitm(anmRecruitm);
        announcement.setAnmCpid(anmCpid);
        announcementRepository.save(announcement);
        return announcement;
    }

    // 삭제 - anmIndex로 Announcement 삭제
    public void deleteAnnouncement(int anmIndex) {
        announcementRepository.deleteByIndex(anmIndex);
    }

    // 수정 - Announcement 업데이트 (anmIndex와 anmCpid는 수정 불가)
    public void updateAnnouncement(int anmIndex, String anmName, Date anmStartDate, Date anmEndDate, String anmEmptype, int anmRecruitm) {
        Announcement announcement = announcementRepository.findAnnouncementByIndex(anmIndex);
        if (announcement != null) {
            announcement.setAnmName(anmName);
            announcement.setAnmStartDate(anmStartDate);
            announcement.setAnmEndDate(anmEndDate);
            announcement.setAnmEmptype(anmEmptype);
            announcement.setAnmRecruitm(anmRecruitm);
            // anmCpid는 수정하지 않음
            announcementRepository.update(announcement);
        }
    }

    // 조회 - 모든 Announcement 찾기
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAllAnnouncements();
    }

    // 조회 - anmIndex로 Announcement 찾기
    public Announcement getAnnouncementByIndex(int anmIndex) {
        return announcementRepository.findAnnouncementByIndex(anmIndex);
    }

    // 조회 - anmName으로 Announcement 찾기
    public List<Announcement> getAnnouncementsByName(String anmName) {
        return announcementRepository.findAnnouncementsByName(anmName);
    }

    // 조회 - 기간으로 Announcement 찾기
    public List<Announcement> getAnnouncementsByPeriod(Date startDate, Date endDate) {
        return announcementRepository.findAnnouncementsByPeriod(startDate, endDate);
    }

    // 조회 - anmEmptype으로 Announcement 찾기
    public List<Announcement> getAnnouncementsByEmptype(String anmEmptype) {
        return announcementRepository.findAnnouncementsByEmptype(anmEmptype);
    }

    // 조회 - anmRecruitm으로 Announcement 찾기
    public List<Announcement> getAnnouncementsByRecruitm(int anmRecruitm) {
        return announcementRepository.findAnnouncementsByRecruitm(anmRecruitm);
    }

    // 조회 - anmCpid로 Announcement 찾기
    public List<Announcement> getAnnouncementsByCompany(Company anmCpid) {
        return announcementRepository.findAnnouncementsByCompany(anmCpid);
    }

    // 추가적인 서비스 메소드들을 여기에 구현할 수 있습니다.
}
