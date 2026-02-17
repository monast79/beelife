package ru.crimea.beelife.dto;

import lombok.Data;

@Data
public class ApiaryDto extends BaseDto {
    private String name;
    private String description;
    private Long userId;
    private String type;
}
