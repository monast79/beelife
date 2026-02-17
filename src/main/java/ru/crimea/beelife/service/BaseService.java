package ru.crimea.beelife.service;

import ru.crimea.beelife.dto.ApiaryDto;
import ru.crimea.beelife.dto.BaseDto;
import ru.crimea.beelife.exception.PermissionDeniedException;
import ru.crimea.beelife.model.BasePersistableObject;
import ru.crimea.beelife.model.User;

import java.io.Serializable;

public interface BaseService<T extends BasePersistableObject, D extends BaseDto> {

    void checkUserPermission(User user) throws PermissionDeniedException;

    User getUserFromObject(Long id);

    D findById(Long id) throws PermissionDeniedException;

    void deleteById(Long id) throws PermissionDeniedException;
}
