package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.Author;
import com.mjc.school.service.implementation.dto.request.AuthorDtoRequest;
import com.mjc.school.service.implementation.dto.response.AuthorDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface AuthorMapper extends com.mjc.school.service.mappers.Mapper<Author, AuthorDtoRequest, AuthorDtoResponse> {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true),
            @Mapping(target = "news", ignore = true),
    })

    Author dtoToModel(AuthorDtoRequest dto);
}
