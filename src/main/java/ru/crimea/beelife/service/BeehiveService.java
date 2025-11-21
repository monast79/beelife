package ru.crimea.beelife.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.crimea.beelife.dto.BeehiveDto;

public interface BeehiveService {

    Page<BeehiveDto> getBeehiveByApiaryId(Long apiaryId, Pageable pageable, String beehiveName);

    BeehiveDto saveBeehive(BeehiveDto beehiveDto);

    void deleteById(Long beehiveId);

    BeehiveDto findById(Long beehiveId);
}
