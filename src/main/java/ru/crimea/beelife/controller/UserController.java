package ru.crimea.beelife.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.crimea.beelife.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("userHome")
    public String userHome(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "userHome";
    }
}
