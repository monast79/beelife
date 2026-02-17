package ru.crimea.beelife.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.crimea.beelife.dto.BeehiveWeightDto;
import ru.crimea.beelife.exception.PermissionDeniedException;
import ru.crimea.beelife.model.BeehiveWeight;

public interface BeehiveWeightService extends BaseService<BeehiveWeight, BeehiveWeightDto> {

    Page<BeehiveWeightDto> getAllByBeehiveId(Long beehiveId, Pageable pageable) throws PermissionDeniedException;
}
