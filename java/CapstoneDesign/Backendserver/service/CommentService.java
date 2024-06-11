package CapstoneDesign.Backendserver.service;

import CapstoneDesign.Backendserver.domain.Board;
import CapstoneDesign.Backendserver.domain.CommentEntity;
import CapstoneDesign.Backendserver.domain.dto.CommentDTO;
import CapstoneDesign.Backendserver.repository.BoardRepository;
import CapstoneDesign.Backendserver.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        Optional<Board> optionalBoard = boardRepository.findById(commentDTO.getBoardId());
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, board);
            return commentRepository.save(commentEntity).getId();
        }

        else {
            return null;
        }
    }

     public List<CommentDTO> findAll(Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(board);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
     }
}


