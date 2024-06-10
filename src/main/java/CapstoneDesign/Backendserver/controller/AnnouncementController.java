package CapstoneDesign.Backendserver.controller;

import CapstoneDesign.Backendserver.SessionConst;
import CapstoneDesign.Backendserver.domain.*;
import CapstoneDesign.Backendserver.service.AnmpService;
import CapstoneDesign.Backendserver.service.AnnouncementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final AnmpService anmpService;

    @GetMapping
    public String announcement_main(Model model, HttpServletRequest request) {

        List<Announcement> announcementList = announcementService.getAllAnnouncements();
        model.addAttribute("announcementList", announcementList);

        Optional<HttpSession> sessionOptional = Optional.ofNullable(request.getSession(false));
        if (sessionOptional.isPresent()) {
            log.info("세션존재");
            HttpSession session = sessionOptional.get();

            if (session.getAttribute(SessionConst.LOGIN_USER) instanceof Company) {
                Company loginCompany = (Company) session.getAttribute(SessionConst.LOGIN_USER);
                model.addAttribute("company", loginCompany);
            } else {
                User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
                model.addAttribute("user", user);
            }
        }

        return "Announcement/announcement_list";
    }

    @PostMapping("addMyAnnouncemnet")
    public String addMyAnnouncement(@RequestParam("selectedAnnouncements") List<Integer> selectedAnnouncements, Model model, HttpServletRequest request) {
        try {
            Optional<HttpSession> sessionOptional = Optional.ofNullable(request.getSession(false));
            if (sessionOptional.isPresent()) {
                log.info("세션존재");
                HttpSession session = sessionOptional.get();

                if (session.getAttribute(SessionConst.LOGIN_USER) instanceof Company) {
                    Company loginCompany = (Company) session.getAttribute(SessionConst.LOGIN_USER);
                    model.addAttribute("company", loginCompany);
                } else {
                    User user = (User) session.getAttribute(SessionConst.LOGIN_USER);

                    for (Integer announcementIdx : selectedAnnouncements) {
                        anmpService.createAnmp(user, announcementService.getAnnouncementByIndex(announcementIdx));
                    }
                    return "redirect:/";

                }
            }
        } catch (IllegalStateException e) {
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            return "redirect:/announcement";
        }
        return "redirect:/announcement";
    }

    @GetMapping("write")
    public String announcement_get(@ModelAttribute("announcement") Announcement announcement, HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            log.info("session is null");
            return "home";
        }

        if (session.getAttribute(SessionConst.LOGIN_USER) instanceof Company) {
            Company loginCompany = (Company) session.getAttribute(SessionConst.LOGIN_USER);
            model.addAttribute("company", loginCompany);
        }
        return "Announcement/announcement_write";
    }

    // @PostMapping("write"a)
//public String announcement_post(@Validated @ModelAttribute("announcement") Announcement announcement, BindingResult bindingResult, Model model) {
//    if (bindingResult.hasErrors()) {
//        // 디버깅을 위해 오류를 출력합니다.
//        bindingResult.getAllErrors().forEach(error -> System.out.println(error.toString()));
//
//        // 폼에서 company 객체를 다시 설정하여 누락된 속성 오류를 방지합니다.
//        model.addAttribute("company", announcement.getAnmCpid());
//        return "Announcement/announcement_write";
//    }
//    announcementService.createAnnouncement(announcement);
//    return "redirect:/announcement";
//}
    @PostMapping("write")
    public String announcement_post(@Validated @ModelAttribute("announcement") Announcement announcement, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.toString()));
            return "Announcement/announcement_write";
        }

        announcementService.createAnnouncement(announcement);
        return "redirect:/announcement";
    }

    @GetMapping("/{idx}")
    public String boardDetail_GET(@PathVariable int idx, Model model,
                                  HttpServletRequest request) {
        Announcement announcement = announcementService.getAnnouncementByIndex(idx);


        Optional<HttpSession> sessionOptional = Optional.ofNullable(request.getSession(false));
        if (sessionOptional.isPresent()) {
            log.info("세션존재");
            HttpSession session = sessionOptional.get();

            if (session.getAttribute(SessionConst.LOGIN_USER) instanceof Company) {
                Company loginCompany = (Company) session.getAttribute(SessionConst.LOGIN_USER);
                model.addAttribute("company", loginCompany);
            } else {
                User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
                model.addAttribute("user", user);
            }
        }
        model.addAttribute("announcement", announcement);

        return "Announcement/announcementDetail";
    }

    @GetMapping("myAnnouncement")
    public String myAnnouncement_GET(HttpServletRequest request, Model model) {
        Optional<HttpSession> sessionOptional = Optional.ofNullable(request.getSession(false));
        if (sessionOptional.isPresent()) {
            log.info("세션존재");
            HttpSession session = sessionOptional.get();

            if (session.getAttribute(SessionConst.LOGIN_USER) instanceof Company) {
                return "redirect:/announcement";
            } else {
                User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
                List<Anmp> anmps = anmpService.getAnmpsByUser(user);
                List<Announcement> announcementList = new ArrayList<>();

                for (Anmp anmp: anmps) {
                    announcementList.add(anmp.getAnmpAnmid());
                }
                    model.addAttribute("announcementList", announcementList);

                    return "Announcement/myAnnouncement";

            }

        }
        return "/login/login_entrance";
    }
}
