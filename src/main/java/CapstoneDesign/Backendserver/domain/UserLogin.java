package CapstoneDesign.Backendserver.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UserLogin {
    @NotBlank
    private String id;
    @NotBlank
    private String pw;
}
