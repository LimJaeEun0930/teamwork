package capweb.capprac.entity;
//일정테이블-옵션값으로 유저인지 회사인지 조회함
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PLAN")
@Getter
@Setter
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int planIndex;//인덱스

    @Column(nullable = false)
    private Date planId;//일정아이디(날짜)

    @Column(nullable = false, length = 60)
    private String planName;//일정이름

    @Column(nullable = false)
    private int planOpt;//일정옵션(옵션값으로 사용자인지 회사인지 조회하기)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planUsid")
    private User planUsid;//일정사용자아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planCpid")
    private Company planCpid;//일정회사아이디

    // No need to define getter and setter methods separately due to the use of Lombok.
}
