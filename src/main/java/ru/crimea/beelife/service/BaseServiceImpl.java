package ru.crimea.beelife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.crimea.beelife.dto.BaseDto;
import ru.crimea.beelife.exception.PermissionDeniedException;
import ru.crimea.beelife.helper.UserHelper;
import ru.crimea.beelife.model.BasePersistableObject;
import ru.crimea.beelife.model.User;

@Service
public abstract class BaseServiceImpl<T extends BasePersistableObject, D extends BaseDto> implements BaseService<T, D> {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private MessageSource messageSource;

    @Override
    public void checkUserPermission(User user) throws PermissionDeniedException {
        User currentUser = userHelper.getCurrentUser();
        if (!user.equals(currentUser)) {
            throw new PermissionDeniedException(messageSource.getMessage("message.user.exception", new Object[]{currentUser.getUsername()}, LocaleContextHolder.getLocale()));
        }
    }
}
