package ru.crimea.beelife.service;

import jakarta.servlet.http.HttpServletRequest;

public interface SecurityService {

    void autologin(String username, String password, HttpServletRequest request);
}
