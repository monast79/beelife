package ru.crimea.beelife.dto;

import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String password;
    private String passwordConfirm;
    private String firstName;
    private String lastName;
    private String email;
}
