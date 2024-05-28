package capweb.capprac;

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
    private int anmIndex;

    @Column(nullable = false, length = 60)
    private String anmName;

    // 시작 날짜와 종료 날짜 필드 추가
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date anmStartDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date anmEndDate;

    @Column(nullable = false, length = 60)
    private String anmEmptype;

    @Column(nullable = false)
    private int anmRecruitm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anmCpid", nullable = false)
    private Company anmCpid;
}
