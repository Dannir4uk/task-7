package ru.itmentor.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.model.Employee;
import ru.itmentor.spring.boot_security.demo.service.RegistrationService;
import ru.itmentor.spring.boot_security.demo.util.EmployeeValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final EmployeeValidator employeeValidator;


    @Autowired
    public AuthController(RegistrationService registrationService, EmployeeValidator employeeValidator) {
        this.registrationService = registrationService;
        this.employeeValidator = employeeValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("employee") Employee employee) {
        return "auth/registration";
    }
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("employee") @Valid Employee employee,
                                      BindingResult bindingResult) {
        employeeValidator.validate(employee, bindingResult);

        if (bindingResult.hasErrors())
            return "/auth/registration";

        registrationService.register(employee);

        return "redirect:/auth/login";
    }
}

