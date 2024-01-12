package com.mjc.school.service.mappers;

import java.util.List;

public interface Mapper<M, Q, R> {
    List<R> modelListToDtoList(List<M> modelList);
    R modelToDto(M model);
    M dtoToModel(Q dto);
}
