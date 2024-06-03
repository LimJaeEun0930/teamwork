package capweb.capprac;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "APP_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usIndex;

    @Column(nullable = false, unique = true, length = 20)
    private String usId;

    @Column(nullable = false, length = 20)
    private String usPw;

    @Column(nullable = false, length = 20)
    private String usName;

    private Date usFixdate;

    @Column(length = 60)
    private String usFixIP;

    @Column(nullable = false)
    private Date usJoindate;

    @Column(nullable = false, length = 60)
    private String usJoinIP;

    public void printfield(){
        System.out.println(this.usId+this.usPw+this.usName+this.usJoindate+this.usJoinIP);
    }
    // Lombok will generate the getters and setters
}
