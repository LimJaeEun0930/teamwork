package capweb.capprac.entity;
//견학참여자테이블-사용자와 견학을 가지고 만들기와 삭제 기능만 수행
//새 견학이 만들어지고 사용자가 참여하려고 할 떄 견학참여자테이블이 만들어진다.
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "TOURP")
@Getter
@Setter
public class Tourp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tourpIndex")
    private int tourpIndex;//인덱스

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourpUsid", nullable = false)
    private USer tourpUsid;//견학참여자아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourpTourid", nullable = false)
    private Tour tourpTourid;//견학아이디

    // 람복 라이브러리를 사용하기 때문에, 별도로 Getter와 Setter 메서드를 정의할 필요가 없습니다.
}
