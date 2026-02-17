package ru.crimea.beelife.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.crimea.beelife.dto.BeehiveDto;
import ru.crimea.beelife.exception.PermissionDeniedException;
import ru.crimea.beelife.model.Beehive;

public interface BeehiveService extends BaseService<Beehive, BeehiveDto>{


    BeehiveDto saveBeehive(BeehiveDto beehiveDto);

}
