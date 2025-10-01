package ru.crimea.beelife.mapper;

import java.util.List;

public interface BaseMapper<D, M> {
    D toDto(M model);
    M toModel(D dto);
    List<D> toDtoList(List<M> modelList);
    List<M> toModelList(List<D> dtoList);
}
