package ru.crimea.beelife.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.crimea.beelife.dto.UserDto;
import ru.crimea.beelife.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends BaseMapper<UserDto, User>{
}
