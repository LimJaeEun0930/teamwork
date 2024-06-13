package CapstoneDesign.Backendserver.controller;

import CapstoneDesign.Backendserver.domain.Company;
import CapstoneDesign.Backendserver.domain.dto.TestCompanyCreateFormData;
import CapstoneDesign.Backendserver.repository.CompanyRepository;
import CapstoneDesign.Backendserver.service.CompanyService;
import CapstoneDesign.Backendserver.util.TestCreateCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class TestCompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private TestCreateCompany testCreateCompany;

    @GetMapping
    public String companyPage() {
        return "/board/companyRegisterForm";
    }
    //0609 회원가입
    @PostMapping("/register")
    public String showCPRegistrationForm(@ModelAttribute TestCompanyCreateFormData testCompanyCreateFormData) {
        Company company = testCreateCompany.TTestCreateCompany(testCompanyCreateFormData);
        companyService.registerCompany(company);
        return "redirect:/company";
    }
}
