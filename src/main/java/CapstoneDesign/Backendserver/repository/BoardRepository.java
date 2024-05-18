package CapstoneDesign.Backendserver.repository;

import CapstoneDesign.Backendserver.domain.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Board boardDTO) {
        em.persist(boardDTO);
    }


    public List<Board> findAll()
    {
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    public Page<Board> findAll(Pageable pageable)
    {
        int page = pageable.getPageNumber();
        int pageLimit = pageable.getPageSize();
        Sort sort = pageable.getSort();

        String sortBy = "id";
        if (sort != null && sort.isSorted()) {
            sortBy = sort.stream().findFirst().get().getProperty();
        }

        TypedQuery<Long> countQuery = em.createQuery(
                "SELECT COUNT(b) FROM Board b", Long.class);

        long total = countQuery.getSingleResult();

        TypedQuery<Board> query = em.createQuery(
                "SELECT b FROM Board b ORDER BY b." + sortBy + " DESC", Board.class);
        query.setFirstResult(page * pageLimit);
        query.setMaxResults(pageLimit);

        List<Board> content = query.getResultList();

        return new PageImpl<>(content, pageable, total);
    }
//    public List<Board> findAll(int page, int pageLimit) {
//        return  em.createQuery(
//                "SELECT b FROM Board b ORDER BY b.id DESC", Board.class)
//                .setFirstResult(page * pageLimit)
//                .setMaxResults(pageLimit)
//                .getResultList();
//    }

    public void updateHits(Long setid)
    {
        em.createQuery("update Board b set b.boardHits =b.boardHits+1 where b.id= :setid")
                .setParameter("setid", setid).executeUpdate();
    }

    public Board findById(Long id)
    {

        Optional<Board> optionalBoard = Optional.ofNullable(em.find(Board.class, id));
        if (optionalBoard.isPresent()) {
            return optionalBoard.get();
        } else {
            return null;
        }
    }

    public void updateBoard(Board board, Long boardId)
    {
        Board boardUpdate = em.find(Board.class, boardId);
        boardUpdate.setBoardTitle(board.getBoardTitle());
        boardUpdate.setBoardContents(board.getBoardContents());

    }

    public void deleteBoard(Long boardId)
    {
        Board board = em.find(Board.class, boardId);
        em.remove(board);
    }


}
