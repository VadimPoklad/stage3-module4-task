package com.mjc.school.service.implementation.dto.response;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthorDtoResponse extends RepresentationModel<AuthorDtoResponse> {
    private Long id;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}
