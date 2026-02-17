package ru.crimea.beelife.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class BeehiveWeightDto extends BaseDto {
    private Long beehiveId;
    private Date measure;
    private Double weight;
}
