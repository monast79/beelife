package ru.crimea.beelife.dto;

import java.sql.Timestamp;
import java.util.Date;

public record BeehiveWeightDto(Long id, Long beehiveId, Date measure, Double weight) {
}
