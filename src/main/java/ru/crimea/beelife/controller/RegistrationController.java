package ru.crimea.beelife.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.crimea.beelife.dto.UserDto;
import ru.crimea.beelife.service.SecurityService;
import ru.crimea.beelife.service.UserService;
import ru.crimea.beelife.validator.UserValidator;


@Controller
@RequiredArgsConstructor
public class RegistrationController {

    @Autowired
    private final UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDto());

        return "registration";
    }


    @PostMapping("/registration")
    public String registrationUser(@ModelAttribute("userForm") @Validated UserDto userForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {

        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "redirect:/login#toregister";
        }

        if (!userService.saveUser(userForm)) {
            return "redirect:/login#toregister";
        }
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm(), request);
        return "redirect:/user/home";
    }
}