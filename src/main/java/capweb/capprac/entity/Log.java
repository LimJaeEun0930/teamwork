package capweb.capprac.entity;
//로그테이블-사용자나 회사의 로그를 기록 옵션값으로 사용자인지 회사인지 조회
import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LOG")
@Getter
@Setter
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logIndex;

    @Column(nullable = false)
    private Date logEnterdate;//접속일자

    @Column(nullable = false, length = 60)
    private String logEnterIP;//접속아이피

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logUsid")
    private User logUsid;//사용자아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logCpid")
    private Company logCpid;//회사아이디

    @Column(nullable = false)
    private int logOpt;//로그옵션(옵션값으로 사용자인지 회사인지 조회 식별)
}
