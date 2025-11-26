package ru.crimea.beelife.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.crimea.beelife.dto.BeehiveWeightDto;
import ru.crimea.beelife.model.BeehiveWeight;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BeehiveWeightMapper extends BaseMapper<BeehiveWeightDto, BeehiveWeight>{

    @Override
    @Mapping(target = "beehiveId", source = "model.beehive.id")
    public BeehiveWeightDto toDto(BeehiveWeight model) ;
}
