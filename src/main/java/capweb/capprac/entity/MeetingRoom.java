package capweb.capprac.entity;
//모임방테이블- 모임방아이디와 이름과 카테고리의 정보만 가지고 참여하는 사용자는 채팅방참여자테이블에서 관리
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "MEETINGROOM")
@Getter
@Setter
@NoArgsConstructor // Lombok을 이용해 기본 생성자를 자동으로 생성합니다.
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mrIndex;//인덱스

    @Column(nullable = false, unique = true, length = 20)
    private String mrMrid;//모임방아이디

    @Column(nullable = false, unique = true, length = 60)
    private String mrName;//모임방이름

    @Column(nullable = false, length = 60)
    private String mrCategory;//모임방카테고리

    // Lombok 어노테이션으로 인해 별도의 getter와 setter 메서드 정의가 필요 없습니다.
}

