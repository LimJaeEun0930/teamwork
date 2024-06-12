package CapstoneDesign.Backendserver.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class TestUserCreateFormData {
    private String usId;
    private String usPw;
    private String usName;
    private String usEmail;
    private String usBirth;
    private String usSex;
    private String usMbti;
}
