package capweb.capprac.entity;
//유저테이블
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
    private int usIndex;//인덱스

    @Column(nullable = false, unique = true, length = 20)
    private String usId;//사용자아이디

    @Column(nullable = false, length = 20)
    private String usPw;//사용자비밀번호

    @Column(nullable = false, length = 20)
    private String usName;//사용자이름

    private Date usFixdate;//수정일자

    @Column(length = 60)
    private String usFixIP;//수정아이피

    @Column(nullable = false)
    private Date usJoindate;//가입일자

    @Column(nullable = false, length = 60)
    private String usJoinIP;//가입아이피

    public void printfield(){
        System.out.println(this.usId+this.usPw+this.usName+this.usJoindate+this.usJoinIP);
    }
    // Lombok will generate the getters and setters
}
