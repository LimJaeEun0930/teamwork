package capweb.capprac.util;

import capweb.capprac.dto.CompanyCreateFormData;
import capweb.capprac.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CreateCompany {
    public Company CreateCompany(CompanyCreateFormData createFormData) {
        Company company = new Company();
        company.setCpId(createFormData.getCpId());
        company.setCpPw(createFormData.getCpPw());
        company.setCpName(createFormData.getCpName());
        company.setCpAddr(createFormData.getCpAddr());
        company.setCpCategory(createFormData.getCpCategory());
        company.setCpMtid(createFormData.getCpMtid());
        company.setCpMtname(createFormData.getCpMtname());
        return company;
    }
}
