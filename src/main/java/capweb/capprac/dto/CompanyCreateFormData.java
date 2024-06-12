package capweb.capprac.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class CompanyCreateFormData {
    private String cpId;
    private String cpPw;
    private String cpName;
    private String cpAddr;
    private String cpCategory;
    private String cpMtid;
    private String cpMtname;
}
