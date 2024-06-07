package capweb.capprac.service;

import capweb.capprac.entity.Announcement;
import capweb.capprac.entity.Company;
import capweb.capprac.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Date;
import java.util.List;
@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    // 만들기 - 새로운 Announcement 생성 및 저장
    @Transactional
    public Announcement createAnnouncement(@ModelAttribute Announcement announcement) {
        // 데이터 검증
        if (announcement.getAnmName() == null || announcement.getAnmName().trim().isEmpty() ||
                announcement.getAnmStartDate() == null || announcement.getAnmEndDate() == null ||
                announcement.getAnmEmptype() == null || announcement.getAnmEmptype().trim().isEmpty() ||
                announcement.getAnmRecruitm() <= 0 || announcement.getAnmCpid() == null) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }

        // 중복 공고 검사
        List<Announcement> existingAnnouncements = announcementRepository.findAnnouncementsByDateRangeAndCompany(
                announcement.getAnmStartDate(), announcement.getAnmEndDate(), announcement.getAnmCpid());
        if (existingAnnouncements.isEmpty()) {
            // 공고 생성 및 저장
            announcementRepository.save(announcement);
            return announcement;
        } else {
            throw new IllegalStateException("해당 기간에 이미 공고가 존재합니다.");
        }
    }

    // 삭제 - anmIndex로 Announcement 삭제
    @Transactional
    public boolean deleteAnnouncement(int anmIndex) {
        Announcement delanms = announcementRepository.findAnnouncementByIndex(anmIndex);
        if (delanms != null) {
            announcementRepository.deleteByIndex(anmIndex);
            return true;
        }
        return false;
    }

    // 수정 - Announcement 업데이트 (anmIndex와 anmCpid는 수정 불가)
    @Transactional
    public boolean updateAnnouncement(@ModelAttribute Announcement announcement) {
        if (announcement.getAnmName() == null || announcement.getAnmName().trim().isEmpty() ||
                announcement.getAnmStartDate() == null || announcement.getAnmEndDate() == null ||
                announcement.getAnmEmptype() == null || announcement.getAnmRecruitm() <= 0) {
            return false;
        }

        Announcement existingAnnouncement = announcementRepository.findAnnouncementByIndex(announcement.getAnmIndex());
        if (existingAnnouncement != null && !existingAnnouncement.getAnmCpid().equals(announcement.getAnmCpid())) {
            // anmCpid가 다르면 업데이트 불가
            return false;
        }

        // anmCpid는 수정하지 않음
        existingAnnouncement.setAnmName(announcement.getAnmName());
        existingAnnouncement.setAnmStartDate(announcement.getAnmStartDate());
        existingAnnouncement.setAnmEndDate(announcement.getAnmEndDate());
        existingAnnouncement.setAnmEmptype(announcement.getAnmEmptype());
        existingAnnouncement.setAnmRecruitm(announcement.getAnmRecruitm());
        announcementRepository.save(existingAnnouncement);
        return true;
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

    // 조회 - 월을 입력받아 해당하는 전체 공지 보기
    public List<Announcement> getAnnouncementsByMonth(int month) {
        return announcementRepository.findAnnouncementsByMonth(month);
    }

    // 추가적인 서비스 메소드들을 여기에 구현할 수 있습니다.
}
