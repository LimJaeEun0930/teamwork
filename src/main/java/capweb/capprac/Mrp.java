package capweb.capprac;

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
    private int mrpIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mrpUsid")
    private User mrpUsid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mrpMrid", nullable = false)
    private MeetingRoom mrpMrid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mrpMtid")
    private Company mrpMtid;

    // No need to define getter and setter methods separately due to the use of Lombok.
}
