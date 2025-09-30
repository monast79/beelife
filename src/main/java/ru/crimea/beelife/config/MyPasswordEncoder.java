package ru.crimea.beelife.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class MyPasswordEncoder extends BCryptPasswordEncoder {

    public MyPasswordEncoder() {
        super(12);
    }
}
