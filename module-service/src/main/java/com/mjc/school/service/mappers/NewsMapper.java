package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.Comment;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.implementation.dto.request.CommentDtoRequest;
import com.mjc.school.service.implementation.dto.response.NewsDtoResponse;
import com.mjc.school.service.implementation.dto.request.NewsDtoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface NewsMapper extends com.mjc.school.service.mappers.Mapper<News, NewsDtoRequest, NewsDtoResponse> {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true),
            @Mapping(target = "author", ignore = true),
            @Mapping(target = "tags", ignore = true),
            @Mapping(target = "comments", ignore = true),
    })
    News dtoToModel(NewsDtoRequest dto);
}
