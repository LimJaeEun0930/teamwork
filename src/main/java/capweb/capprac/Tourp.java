package capweb.capprac;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TOURP")
@Getter
@Setter
public class Tourp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tourpIndex")
    private int tourpIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourpUsid", nullable = false)
    private User tourpUsid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourpTourid", nullable = false)
    private Tour tourpTourid;

    // 람복 라이브러리를 사용하기 때문에, 별도로 Getter와 Setter 메서드를 정의할 필요가 없습니다.
}
