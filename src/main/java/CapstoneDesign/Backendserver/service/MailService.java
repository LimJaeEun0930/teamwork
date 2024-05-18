package CapstoneDesign.Backendserver.service;

import CapstoneDesign.Backendserver.mail.MailVo;
import CapstoneDesign.Backendserver.repository.UserRepository;
import jakarta.mail.SendFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {


    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    @Value("${spring.mail.username}") //@Value는 런타임에 변수로 주입된다.static이 있으면 안된다. null반환됨
    // https://growth-coder.tistory.com/176
    private String senderEmail;



    public void CreateMail(MailVo mailVo) {

        log.info("Sender {}", senderEmail);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailVo.getReceiver());
            message.setFrom(senderEmail);
            message.setSubject(mailVo.getTitle());
            message.setText(mailVo.getContent());

            mailSender.send(message);

    }

    public void sendingAuthenticationMail(String userMail, Model model) {
        String randomNumber = String.valueOf(createRandomNumber());
        model.addAttribute("randomNumber",randomNumber);

        //이메일중복 확인기능 넣어야한다.
        String text="홈페이지를 방문해주셔서 감사합니다."+
                "\n"+ "인증번호는 "+randomNumber+"입니다." +
                "\n" +
                "해당 인증번호를 인증번호 확인란에 기입해주세요.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userMail);
        message.setFrom(senderEmail);
        message.setSubject("요청하신 인증메일입니다.");
        message.setText(text);

        mailSender.send(message);

    }

    private int createRandomNumber() {
        int value = (int) (Math.random()*888888) + 111111;
        return value;
    }
}
