package CapstoneDesign.Backendserver.controller;

import CapstoneDesign.Backendserver.mail.MailVo;
import CapstoneDesign.Backendserver.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;

    @GetMapping
    public String MailPage()
    {
        return "Mail";
    }

    @PostMapping()
    public String MailSend(@ModelAttribute("mailVo") MailVo mailVo)
    {
        log.info("Method executed {}", mailVo.getReceiver());
        mailService.CreateMail(mailVo);
        return "Mail";
    }


}
