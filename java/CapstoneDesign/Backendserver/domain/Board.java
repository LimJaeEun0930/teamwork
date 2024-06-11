package CapstoneDesign.Backendserver.domain;

import java.util.ArrayList;
import java.util.List;

import CapstoneDesign.Backendserver.domain.dto.CommentDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @Column
    private int fileAttached;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFile> boardFileList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentDTO> commentsList = new ArrayList<>();

    @Column
    private String boardPass;
}

