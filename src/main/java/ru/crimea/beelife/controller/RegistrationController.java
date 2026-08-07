package ru.crimea.beelife.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.crimea.beelife.dto.UserDto;
import ru.crimea.beelife.service.SecurityService;


@Controller
@RequiredArgsConstructor
public class RegistrationController {

//    @Autowired
//    private final UserService userService;

    @Autowired
    private SecurityService securityService;

//    @Autowired
//    private UserValidator userValidator;


//    @GetMapping("/registration")
//    public String registration(Model model) {
//        model.addAttribute("userForm", new UserDto());
//
//        return "registration";
//    }

    @GetMapping("/confirm-registration")
    String confirm(
            @RequestParam UserDto userDto, HttpServletRequest request
    ) {

        securityService.autologin(userDto.getUsername(), userDto.getPasswordConfirm(), request);
        return "redirect:/user/home";
    }

//    @PostMapping("/registration")
//    public String registrationUser(@ModelAttribute("userForm") @Validated UserDto userForm, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes) {
//
//        userValidator.validate(userForm, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            bindingResult.getAllErrors().forEach(error -> redirectAttributes.addFlashAttribute("message", error.getDefaultMessage()));
//            return "login?error";
//        }
//
//        if (!userService.saveUser(userForm)) {
//            redirectAttributes.addFlashAttribute("message", "Can not save to DB" );
//            return "login?error";
//        }
//        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm(), request);
//        return "redirect:/user/home";
//    }
}