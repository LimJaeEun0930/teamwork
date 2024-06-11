package CapstoneDesign.Backendserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import CapstoneDesign.Backendserver.domain.Board;
import CapstoneDesign.Backendserver.domain.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(Board board);
}


