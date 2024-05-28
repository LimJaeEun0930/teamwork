package capweb.capprac;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "COMPANY")
@Getter
@Setter
@NoArgsConstructor // 기본 생성자 자동 생성
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cpIndex;

    @Column(nullable = false, unique = true, length = 20)
    private String cpId;

    @Column(nullable = false, length = 20)
    private String cpPw;

    @Column(nullable = false, unique = true, length = 60)
    private String cpName;

    @Column(nullable = false, unique = true, length = 100)
    private String cpAddr;

    @Column(nullable = false, length = 60)
    private String cpCategory;

    @Column(nullable = false, unique = true, length = 20)
    private String cpMtid;

    @Column(nullable = false, length = 20)
    private String cpMtname;

    private Date cpFixdate;

    @Column(length = 60)
    private String cpFixIP;

    @Column(nullable = false)
    private Date cpJoindate;

    @Column(nullable = false, length = 60)
    private String cpJoinIP;
}


