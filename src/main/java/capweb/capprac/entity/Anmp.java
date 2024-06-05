package capweb.capprac.entity;
//참여자테이블: 공지나 견학,모임방에 참여하고 있는 인원들을 관리하기 위해 만듬
//공지참여자(유저아이디와 공지아이디를 가지고 담거나 뺴기만 가능)
//새로운 공지가 만들어지고 사용자가 참가하려고 할때 공지참여자를 만들어주기
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ANMP")
@Getter
@Setter
public class Anmp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int anmpIndex;//인덱스

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anmpUsid", nullable = false)
    private User anmpUsid;//공지참여자아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anmpAnmid", nullable = false)
    private Announcement anmpAnmid;//공지아이디
}
