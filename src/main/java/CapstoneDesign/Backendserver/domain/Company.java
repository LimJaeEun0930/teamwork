package CapstoneDesign.Backendserver.domain;

//회사테이블(기본은 회사아이디와 비밀번호를 사용하고 채팅방참여자에선 멘토아이디를 이용하고 멘토아이디는 수정 불가)
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

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int cpIndex;//인덱스
    @Id
    @Column(nullable = false, unique = true, length = 20)
    private String cpId;//사용자아이디

    @Column(nullable = false, length = 20)
    private String cpPw;//사용자비밀번호

    @Column(nullable = false, unique = true, length = 60)
    private String cpName;//사용자이름

    @Column(nullable = false, unique = true, length = 100)
    private String cpAddr;//사용자주소

    @Column(nullable = false, length = 60)
    private String cpCategory;


}