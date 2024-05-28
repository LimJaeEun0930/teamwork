package capweb.capprac;

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
    private int tourIndex;

    @Column(nullable = false)
    private Date tourDay;

    @Column(nullable = false, length = 60)
    private String tourName;

    @Column(nullable = false)
    private int tourRecruitm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourCpid", nullable = false)
    private Company tourCpid;

    // Lombok 사용으로 인해 Getter와 Setter 메서드를 따로 정의할 필요가 없습니다.
}
