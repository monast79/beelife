package ru.crimea.beelife.dto;

import lombok.Data;

@Data
public class BeehiveDto extends BaseDto {
    private String name;
    private String description;
    private String type;
    private Integer frame;
    private Long apiaryId;
    //ToDo Converter SPI
    // private ApiaryDto apiary;
}
