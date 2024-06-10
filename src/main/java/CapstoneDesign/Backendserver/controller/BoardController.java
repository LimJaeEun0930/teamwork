package CapstoneDesign.Backendserver.controller;

import CapstoneDesign.Backendserver.SessionConst;
import CapstoneDesign.Backendserver.domain.Board;
import CapstoneDesign.Backendserver.domain.JobCategory;
import CapstoneDesign.Backendserver.domain.User;
import CapstoneDesign.Backendserver.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

     @ModelAttribute("jobCategory")
    public JobCategory[] JobCategories() {
        return JobCategory.values();
    }


    @GetMapping("/board/write")
    public String writeBoard_GET(@ModelAttribute("board") Board board, Model model, HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);

        if (session != null && user != null) {
            log.info("user 들어옴?1{}", user.toString());
            model.addAttribute("user", user);
//            model.addAttribute("board", board);이코드 없어도 @ModelAttribute에 담김.
            return "board/writeBoard";
        } else {
            return  "redirect:/";
        }

    }

    @PostMapping("/board/write") //board의 필드들이 들어오지 않는 문제 있었는데,Board에 setter설정해주니 됨
    public String writeBoard_POST(@ModelAttribute Board board,  HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        log.info("user 들어옴?{}", user.toString());
        board.setBoardWriter(user.getId()); //유저아이디 넣어야 한다.
        log.info("board = {}", board);

        boardService.save(board);
        return "redirect:/board/paging";
    }

    @GetMapping("/board/edit/{board_id}") // PathVariable로 받아도 모델에 추가가 되는것 같다
    public String boardEdit_GET(@PathVariable Long board_id,@RequestParam Long page ,
                                Model model,HttpServletRequest request)
    {
        Board board = boardService.findById(board_id);
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("user", user);
        model.addAttribute("boardUpdate", board);
        model.addAttribute("page", page);
        return "board/boardEdit";
    }

    @PostMapping("/board/edit/{board_id}")
    public String boardEdit_POST(@PathVariable Long board_id,@RequestParam Long page,
                                 @ModelAttribute Board board)
    {
        boardService.update(board,board_id);//error. dirty checking으로 메서드 하나 만들어야할듯

        return "redirect:/board/" + board_id+"?page="+page;
    }



    //@GetMapping("/boardList")
    public String boardList_GET(Model model)
    {
        List<Board> boardList = boardService.findAll();
        model.addAttribute("boardList", boardList);

        return "board/boardList";
    }

    @GetMapping("/board/{id}")
    public String boardDetail_GET(@PathVariable Long id,@RequestParam Long page, Model model,
                                  HttpServletRequest request)
    {
        boardService.updateHits(id); //조회수 증가
        Board board = boardService.findById(id);

        Optional<HttpSession> sessionOptional = Optional.ofNullable(request.getSession(false));
        if (sessionOptional.isPresent()) {
            log.info("세션존재");
            HttpSession session = sessionOptional.get();
            model.addAttribute("page", page);

            if (//로그인유저와 게시글의 유저가 같다면
                    (session.getAttribute(SessionConst.LOGIN_USER)!= null) &&
                            ((User) session.getAttribute(SessionConst.LOGIN_USER)).getId()
                    .equals(board.getBoardWriter())) {
                model.addAttribute("user", session.getAttribute(SessionConst.LOGIN_USER));
            } else {
                log.info("세션은있는데..");
            }
        }
        model.addAttribute("board", board);
        return "board/boardDetail";
    }

    @GetMapping("board/delete/{boardId}")
    public String boardDelete_GET(@PathVariable Long boardId)
    {
        boardService.deleteBoard(boardId);

        return "redirect:/board/paging";
    }

    @GetMapping("board/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<Board> boardList = boardService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("selectedCategory", null);

        return "board/paging";
    }

    @GetMapping("board/category")
    public String category(@RequestParam JobCategory category, @PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<Board> boardList = boardService.findByCategory(category, pageable);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("selectedCategory", category);

        return "board/paging";
    }
}

