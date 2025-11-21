package ru.crimea.beelife.dto;

import lombok.Data;

@Data
public class ApiaryDto {
    private Long id;
    private String name;
    private String description;
    private Long userId;
    private String type;
}
