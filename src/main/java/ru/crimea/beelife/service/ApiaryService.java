package ru.crimea.beelife.service;

import ru.crimea.beelife.dto.ApiaryDto;
import ru.crimea.beelife.exception.PermissionDeniedException;
import ru.crimea.beelife.model.Apiary;

import java.util.List;

public interface ApiaryService extends BaseService<Apiary, ApiaryDto> {

    List<ApiaryDto> getApiariesByUserId(Long userId) throws PermissionDeniedException;

    boolean saveApiary(ApiaryDto apiaryDto) throws PermissionDeniedException;

    void deleteById(Long beehiveId) throws PermissionDeniedException;
}

