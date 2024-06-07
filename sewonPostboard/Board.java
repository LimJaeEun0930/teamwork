package CapstoneDesign.Backendserver.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BoardTimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    @NotBlank
    private String boardWriter;
    @NotBlank
    private String boardTitle;
    @NotBlank
    private String boardContents;

    private int boardHits;
    
    //블로그 필드 추가
    private String companyType; //기업형태 추가
    private String applicationPeriod; //접수기간 추가
    private String jobPosition; //모집직무 추가
    private String workLocation; //근무지역 추가

    //업데이트 필드 추가
    private String industry; // 업직종 추가
    private String employmentType; // 고용형태 추가
    private int recruitmentCount; // 모집인원 추가

    //게시판목록 필드 추가
    private String companyName; // 기업명 추가
    private LocalDate applicationDeadline; // 마감일 추가


    public void setBoardWriter(String boardWriter) {
        this.boardWriter = boardWriter;
    }


    public Board orElseThrow(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
    }
}
