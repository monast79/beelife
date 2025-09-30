package ru.crimea.beelife.mapper;

public interface BaseMapper<D, M> {
    D toDto(M model);
    M toModel(D dto);
}
