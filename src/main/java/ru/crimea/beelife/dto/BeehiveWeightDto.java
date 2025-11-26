package ru.crimea.beelife.dto;

import java.sql.Date;

public record BeehiveWeightDto(Long id, Long beehiveId, Date measure, Double weight) {
}
