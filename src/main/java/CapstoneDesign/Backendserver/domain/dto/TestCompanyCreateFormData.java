package CapstoneDesign.Backendserver.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TestCompanyCreateFormData {
    private String cpId;
    private String cpPw;
    private String cpName;
    private String cpAddr;
    private String cpCategory;
}
