package ru.crimea.beelife.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.crimea.beelife.dto.BeehiveWeightDto;

public interface BeehiveWeightService {

    Page<BeehiveWeightDto> getAllByBeehiveId(Long beehiveId, Pageable pageable);
}
