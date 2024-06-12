package CapstoneDesign.Backendserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CompanyLogin {

    private String cpId;//cp아이디
    private String cpPw;//cp비밀번호
}
