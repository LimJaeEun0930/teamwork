package capweb.capprac;

import capweb.capprac.MeetingRoom;
import capweb.capprac.Mrp;
import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MESSAGE")
@Data // Lombok 어노테이션으로 getter, setter, toString, equals, hashCode 메소드 자동 생성
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int msgIndex;

    @Column(nullable = false)
    private String msgContent;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date msgTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msgSenderusid", nullable = false)
    private Mrp msgSenderusid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msgMrid", nullable = false)
    private MeetingRoom msgMrid;
}
