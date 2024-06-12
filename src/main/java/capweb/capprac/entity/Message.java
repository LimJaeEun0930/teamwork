package capweb.capprac.entity;
//메세지 테이블- 메세지내용과 시간과 모임방참여자를 통해 송신자를 알아내고 모임방을 통해 해당 모임방을 알아냄
import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MESSAGE")
@Data // Lombok 어노테이션으로 getter, setter, toString, equals, hashCode 메소드 자동 생성
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int msgIndex;//인덱스

    @Column(nullable = false)
    private String msgContent;//메세지내용

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date msgTime;//메세지시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msgSenderusid", nullable = false)
    private Mrp msgSenderusid;//메세지송신자아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msgMrid", nullable = false)
    private MeetingRoom msgMrid;//메세지모임방아이디
}
