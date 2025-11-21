package ru.crimea.beelife.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.crimea.beelife.dto.BeehiveDto;
import ru.crimea.beelife.model.Beehive;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BeehiveMapper extends BaseMapper<BeehiveDto, Beehive> {

    @Override
    @Mapping(target = "type", source = "hiveType")
    @Mapping(target = "apiaryId", source = "model.apiary.id")
    public BeehiveDto toDto(Beehive model) ;


    //ToDo
    //AfterMapping for apiary - should go to db via repository to find Entity
    @Override
    @Mapping(target="apiary",ignore = true)
    @Mapping(target = "hiveType", source = "type")
    public Beehive toModel(BeehiveDto dto) ;
}
