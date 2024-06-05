package capweb.capprac.entity;
//견학테이블-견학에 관련된 정보를 가지고 참가하는 인원은 견학참여자에서 관리
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TOUR")
@Getter
@Setter
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tourIndex;//인덱스

    @Column(nullable = false)
    private Date tourDay;//견학일자

    @Column(nullable = false, length = 60)
    private String tourName;//견학명

    @Column(nullable = false)
    private int tourRecruitm;//견학제한인원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourCpid", nullable = false)
    private Company tourCpid;//견학회사아이디

    // Lombok 사용으로 인해 Getter와 Setter 메서드를 따로 정의할 필요가 없습니다.
}
