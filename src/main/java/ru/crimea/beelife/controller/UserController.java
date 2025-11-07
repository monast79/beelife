package ru.crimea.beelife.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.crimea.beelife.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

}
