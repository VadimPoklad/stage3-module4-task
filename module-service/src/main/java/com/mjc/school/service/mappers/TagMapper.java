package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.implementation.dto.request.AuthorDtoRequest;
import com.mjc.school.service.implementation.dto.request.TagDtoRequest;
import com.mjc.school.service.implementation.dto.response.TagDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TagMapper extends com.mjc.school.service.mappers.Mapper<Tag, TagDtoRequest, TagDtoResponse> {
    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    Tag dtoToModel(TagDtoRequest dto);
}