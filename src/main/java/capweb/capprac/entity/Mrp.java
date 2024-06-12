package capweb.capprac.entity;
//모임방참여자테이블(유저와 모임방과 회사를 가지고 유저나 멘토만 있을수도,같이 있을수도 있고 만들어지거나 삭제하는 기능만수행)
//새 미팅룸이 만들어질때마다 모임방참여자를 만들어줘야한다
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "MRP")
@Getter
@Setter
public class Mrp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mrpIndex;//인덱스

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mrpUsid")
    private USer mrpUsid;//모임방참여자아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mrpMrid", nullable = false)
    private MeetingRoom mrpMrid;//모임방아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mrpMtid")
    private Company mrpMtid;//멘토아이디

    // No need to define getter and setter methods separately due to the use of Lombok.
}
