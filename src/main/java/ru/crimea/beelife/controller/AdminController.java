package ru.crimea.beelife.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.crimea.beelife.dto.UserDto;
import ru.crimea.beelife.service.UserService;

@Controller
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final UserService userService;

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public String listUsers(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {
        String sortField = sort[0];
        String sortDirection = sort[1];
        Sort.Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Order order = new Order(direction, sortField);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));
        Page<UserDto> userPage = userService.allActiveUsers(pageable, keyword);

        if (keyword != null) {
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("userPage", userPage);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", userPage.getNumber() + 1);
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        return "adminHome";
    }

    @GetMapping("/admin/home/delete/{id}")
    public String handleDeleteUser(@PathVariable("id")  Long userId, RedirectAttributes redirectAttributes) {
        try {
        userService.deleteUser(userId);
            redirectAttributes.addFlashAttribute("message", "The User with id=" + userId + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/admin/home";
    }

    @GetMapping("/admin/home/user/{id}")
    @ResponseBody
    public UserDto getUser(@PathVariable("id")  Long userId) {
        return userService.getUser(userId);
    }


    @PostMapping("/admin/home/updateUser")
    public String updateUser(@ModelAttribute("userForm") @Validated UserDto userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(userForm);
            redirectAttributes.addFlashAttribute("message", "The User " + userForm.getUsername() + " has been updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/home";
    }
    }