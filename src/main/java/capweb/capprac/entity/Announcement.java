package capweb.capprac.entity;
//공지테이블(하나의 회사는 해당기간동안 하나의 공지만 가능)
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Date; // Date 클래스 추가

@Entity
@Table(name = "ANNOUNCEMENT")
@Getter
@Setter
@NoArgsConstructor // 기본 생성자 자동 생성
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int anmIndex;//공지인덱스

    @Column(nullable = false, length = 60)
    private String anmName;//공지이름

    // 시작 날짜와 종료 날짜 필드 추가
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date anmStartDate;//공지시작날짜

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date anmEndDate;//공지종료날짜

    @Column(nullable = false, length = 60)
    private String anmEmptype;//공지고용타입

    @Column(nullable = false)
    private int anmRecruitm;//공지제한인원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anmCpid", nullable = false)
    private Company anmCpid;//공지올린산업체아이디
}
