package ru.crimea.beelife.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.crimea.beelife.model.User;
import ru.crimea.beelife.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

   @Autowired
    private final UserService userService;

    @GetMapping("/admin/home")
    public String adminHome(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "adminHome";
    }

//    @ModelAttribute("allUsers")
//    public List<User> users() {
//        return userService.allUsers();
//    }
}