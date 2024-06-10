package CapstoneDesign.Backendserver.converter;

import CapstoneDesign.Backendserver.domain.Company;
import CapstoneDesign.Backendserver.repository.CompanyRepository;
import CapstoneDesign.Backendserver.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StringToCompanyConverter implements Converter<String, Company> {

    private final CompanyService companyService;

    @Override
    public Company convert(String source) {
        return (Company) companyService.findCompany(source);
    }
}
