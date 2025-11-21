package ru.crimea.beelife.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.crimea.beelife.dto.ApiaryDto;
import ru.crimea.beelife.model.Apiary;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiaryMapper extends  BaseMapper<ApiaryDto, Apiary>{

    @Override
    @Mapping(target = "type", source = "type")
    public ApiaryDto toDto(Apiary model) ;


    @Override
    @InheritInverseConfiguration
    public Apiary toModel(ApiaryDto dto) ;
}
