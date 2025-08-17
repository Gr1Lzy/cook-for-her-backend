package com.github.cookforher.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class DtoMapper<E, D> {

  private final Class<E> entityClass;
  private final Class<D> dtoClass;
  private final ModelMapper modelMapper;

  public DtoMapper(Class<E> entityClass, Class<D> dtoClass) {
    this.entityClass = entityClass;
    this.dtoClass = dtoClass;
    this.modelMapper = new ModelMapper();
    this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  public E toEntity(D dto) {
    return modelMapper.map(dto, entityClass);
  }

  public D toDto(E entity) {
    return modelMapper.map(entity, dtoClass);
  }
}
