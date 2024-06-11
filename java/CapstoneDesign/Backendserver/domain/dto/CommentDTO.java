package CapstoneDesign.Backendserver.domain.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long id;//작성자 id
    private String commentWriter;//작성자
    private String commentContents;//내용
    private Long boardId; //게시글번호
    private LocalDateTime commentCreatedTime;//작성시간
}
