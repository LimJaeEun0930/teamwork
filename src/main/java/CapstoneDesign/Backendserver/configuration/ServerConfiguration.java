package CapstoneDesign.Backendserver.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class ServerConfiguration {

    public JavaMailSender MailSender() {
        return new JavaMailSenderImpl();
    }

}
