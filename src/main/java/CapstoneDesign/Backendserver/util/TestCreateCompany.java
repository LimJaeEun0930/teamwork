package CapstoneDesign.Backendserver.util;

import CapstoneDesign.Backendserver.domain.dto.TestCompanyCreateFormData;
import CapstoneDesign.Backendserver.domain.Company;
import org.springframework.stereotype.Component;

@Component
public class TestCreateCompany {
    public Company TTestCreateCompany(TestCompanyCreateFormData createFormData) {
        Company company = new Company();
        company.setCpId(createFormData.getCpId());
        company.setCpPw(createFormData.getCpPw());
        company.setCpName(createFormData.getCpName());
        company.setCpAddr(createFormData.getCpAddr());
        company.setCpCategory(createFormData.getCpCategory());
        return company;
    }
}
