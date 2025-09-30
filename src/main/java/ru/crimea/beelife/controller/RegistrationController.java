package ru.crimea.beelife.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.crimea.beelife.dto.UserDto;
import ru.crimea.beelife.mapper.UserMapper;
import ru.crimea.beelife.model.User;
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

    @Autowired
    private final UserMapper userMapper;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }


    @PostMapping("/registration")
    public String registrationUser(@ModelAttribute("userForm") @Validated UserDto userForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {

        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "redirect:/login#toregister";
        }
        User user = userMapper.toModel(userForm);

        if (!userService.saveUser(user)) {
            return "redirect:/login#toregister";
        }
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm(), request);
        return "redirect:/user/home";
    }
}