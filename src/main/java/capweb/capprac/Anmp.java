package capweb.capprac;
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
    private int anmpIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anmpUsid", nullable = false)
    private User anmpUsid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anmpAnmid", nullable = false)
    private Announcement anmpAnmid;
}
