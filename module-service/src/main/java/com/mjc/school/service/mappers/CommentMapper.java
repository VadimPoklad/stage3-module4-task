package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.Comment;
import com.mjc.school.service.implementation.dto.response.CommentDtoResponse;
import com.mjc.school.service.implementation.dto.request.CommentDtoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface CommentMapper extends com.mjc.school.service.mappers.Mapper<Comment, CommentDtoRequest, CommentDtoResponse> {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true),
            @Mapping(target = "news", ignore = true),
    })
    Comment dtoToModel(CommentDtoRequest dto);
}

