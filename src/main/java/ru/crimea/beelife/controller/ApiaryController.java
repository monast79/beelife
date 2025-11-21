package ru.crimea.beelife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.crimea.beelife.dto.ApiaryDto;
import ru.crimea.beelife.model.User;
import ru.crimea.beelife.service.ApiaryService;

@Controller
public class ApiaryController {

    @Autowired
    private ApiaryService apiaryService;

    @GetMapping("/user/home")
    public String getApiaries(Authentication authentication, Model model) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        model.addAttribute("apiaries", apiaryService.getApiariesByUserId(userId));
        model.addAttribute("userId", userId);
        return "userHome";
    }

    @PostMapping("/user/home/apiary/add")
    public String addApiary(@ModelAttribute("apiaryForm") @Validated ApiaryDto apiaryForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (!apiaryService.saveApiary(apiaryForm)) {
            redirectAttributes.addFlashAttribute("message", "Can not save to DB" );
        }
        return "redirect:/user/home";
    }

}