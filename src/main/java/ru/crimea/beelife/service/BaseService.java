package ru.crimea.beelife.service;

import ru.crimea.beelife.dto.BaseDto;
import ru.crimea.beelife.exception.PermissionDeniedException;
import ru.crimea.beelife.model.BasePersistableObject;
import ru.crimea.beelife.model.User;

public interface BaseService<T extends BasePersistableObject, D extends BaseDto> {

    void checkUserPermission(User user) throws PermissionDeniedException;

    User getUserFromObjectId(Long id);

    User getUserFromParentObjectId(Long id);

    T findById(Long id);

    D findDtoById(Long id) throws PermissionDeniedException;

    void deleteById(Long id) throws PermissionDeniedException;
}
