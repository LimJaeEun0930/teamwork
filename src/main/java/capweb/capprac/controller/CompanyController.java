package capweb.capprac.controller;

import capweb.capprac.dto.CompanyCreateFormData;
import capweb.capprac.dto.USerCreateFormData;
import capweb.capprac.entity.Company;
import capweb.capprac.entity.USer;
import capweb.capprac.repository.CompanyRepository;
import capweb.capprac.service.CompanyService;
import capweb.capprac.util.CreateCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CreateCompany createCompany;

    @GetMapping
    public String companyPage() {
        return "/board/companyRegisterForm";
    }
    //0609 회원가입
    @PostMapping("/register")
    public String showCPRegistrationForm(@ModelAttribute CompanyCreateFormData companyCreateFormData) {
        Company company = createCompany.CreateCompany(companyCreateFormData);
        companyService.registerCompany(company);
        return "redirect:/company";
    }
}
