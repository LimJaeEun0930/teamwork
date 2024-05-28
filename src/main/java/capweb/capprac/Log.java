package capweb.capprac;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LOG")
@Getter
@Setter
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logIndex;

    @Column(nullable = false)
    private Date logEnterdate;

    @Column(nullable = false, length = 60)
    private String logEnterIP;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logUsid")
    private User logUsid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logCpid")
    private Company logCpid;

    @Column(nullable = false)
    private int logOpt;
}
