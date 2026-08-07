package ru.crimea.beelife.mapper;

import org.mapstruct.*;
import ru.crimea.beelife.dto.BeehiveDto;
import ru.crimea.beelife.model.Beehive;
import ru.crimea.beelife.service.ApiaryService;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ApiaryService.class)
public interface BeehiveMapper extends BaseMapper<BeehiveDto, Beehive> {

  @Override
  @Mapping(target = "type", source = "hiveType")
  @Mapping(target = "apiaryId", source = "model.apiary.id")
  BeehiveDto toDto(Beehive model);


  @Override
  @Mapping(target = "apiary", source = "apiaryId")
  @Mapping(target = "hiveType", source = "type")
  Beehive toModel(BeehiveDto dto);
}
