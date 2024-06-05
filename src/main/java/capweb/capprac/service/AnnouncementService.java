package capweb.capprac.service;

import capweb.capprac.entity.Announcement;
import capweb.capprac.entity.Company;
import capweb.capprac.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    // 만들기 - 새로운 Announcement 생성 및 저장
    //공지아이디,공지기간,공지고용타입,제한인원,회사이름등을 반드시 입력
    //같은 기간에 같은회사는 공지 하나만 가능 만약 검사해서 있으면 못 만들게하기
    @Transactional
    public Announcement createAnnouncement(String anmName, Date anmStartDate, Date anmEndDate, String anmEmptype, int anmRecruitm, Company anmCpid) {
        if (anmName == null || anmName.trim().isEmpty() || anmStartDate == null || anmEndDate == null || anmEmptype == null || anmEmptype.trim().isEmpty() || anmRecruitm == 0 || anmCpid == null) {
            throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
        List<Announcement> existanms = announcementRepository.findAnnouncementsByDateRangeAndCompany(anmStartDate, anmEndDate, anmCpid);
        if (existanms.isEmpty()) {
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
        else {
            // 이미 존재하는 공고가 있을 경우 처리 로직을 여기에 추가하세요.
            // 예를 들어, 사용자에게 알림을 보내거나, 다른 행동을 취할 수 있습니다.
            throw new IllegalStateException("해당 기간에 이미 공고가 존재합니다.");
        }
    }
    // 삭제 - anmIndex로 Announcement 삭제
    @Transactional
    public boolean deleteAnnouncement(int anmIndex) {
        Announcement delanms = announcementRepository.findAnnouncementByIndex(anmIndex);
        if(delanms!=null) {
            announcementRepository.deleteByIndex(anmIndex);
            return true;
        }
        return false;
    }
    // 수정 - Announcement 업데이트 (anmIndex와 anmCpid는 수정 불가)
    @Transactional
    public boolean updateAnnouncement(int anmIndex, String anmName, Date anmStartDate, Date anmEndDate, String anmEmptype, int anmRecruitm,Company anmCpid) {
        if (anmName == null || anmName.trim().isEmpty() || anmStartDate == null || anmEndDate == null || anmEmptype == null || anmRecruitm == 0 || anmCpid == null) {
            return false;
            //throw new IllegalArgumentException("필수 필드가 비어있습니다.");
        }
        List<Announcement> existanms = announcementRepository.findAnnouncementsByDateRangeAndCompany(anmStartDate, anmEndDate, anmCpid);
        if (existanms.isEmpty()) {
            Announcement announcement = announcementRepository.findAnnouncementByIndex(anmIndex);
            if (announcement != null) {
                announcement.setAnmName(anmName);
                announcement.setAnmStartDate(anmStartDate);
                announcement.setAnmEndDate(anmEndDate);
                announcement.setAnmEmptype(anmEmptype);
                announcement.setAnmRecruitm(anmRecruitm);
                // anmCpid는 수정하지 않음
                announcementRepository.update(announcement);
                return true;
            }
            return false;
        }
        return  false;
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
