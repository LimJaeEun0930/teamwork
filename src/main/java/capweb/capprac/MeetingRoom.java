package capweb.capprac;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MEETINGROOM")
@Getter
@Setter
@NoArgsConstructor // Lombok을 이용해 기본 생성자를 자동으로 생성합니다.
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mrIndex;

    @Column(nullable = false, unique = true, length = 20)
    private String mrMrid;

    @Column(nullable = false, unique = true, length = 60)
    private String mrName;

    @Column(nullable = false, length = 60)
    private String mrCategory;

    // Lombok 어노테이션으로 인해 별도의 getter와 setter 메서드 정의가 필요 없습니다.
}

