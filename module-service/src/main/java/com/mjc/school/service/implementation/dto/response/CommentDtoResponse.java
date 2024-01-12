package com.mjc.school.service.implementation.dto.response;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommentDtoResponse extends RepresentationModel<CommentDtoResponse> {
    private Long id;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}
