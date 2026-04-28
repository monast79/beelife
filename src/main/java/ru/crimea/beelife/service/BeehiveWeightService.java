package ru.crimea.beelife.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.crimea.beelife.dto.BeehiveWeightDto;
import ru.crimea.beelife.exception.PermissionDeniedException;
import ru.crimea.beelife.model.BeehiveWeight;

import java.util.Date;
import java.util.List;

public interface BeehiveWeightService extends BaseService<BeehiveWeight, BeehiveWeightDto> {

    List<BeehiveWeightDto> getAllByBeehiveId(Long beehiveId, Date fromDate) throws PermissionDeniedException;
}
