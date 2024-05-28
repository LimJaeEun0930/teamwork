package capweb.capprac;

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
    private int planIndex;

    @Column(nullable = false)
    private Date planId;

    @Column(nullable = false, length = 60)
    private String planName;

    @Column(nullable = false)
    private int planOpt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planUsid")
    private User planUsid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planCpid")
    private Company planCpid;

    // No need to define getter and setter methods separately due to the use of Lombok.
}
