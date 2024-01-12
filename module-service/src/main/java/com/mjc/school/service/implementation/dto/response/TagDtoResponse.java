package com.mjc.school.service.implementation.dto.response;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class TagDtoResponse extends RepresentationModel<TagDtoResponse> {
    private Long id;
    private String name;
}
