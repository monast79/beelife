package ru.crimea.beelife.service;

import ru.crimea.beelife.dto.ApiaryDto;

import java.util.List;

public interface ApiaryService {

    List<ApiaryDto> getApiariesByUserId(Long userId);

    boolean saveApiary(ApiaryDto apiaryDto);

    ApiaryDto findById(Long apiaryId);
}

