package com.mjc.school.service.implementation.dto.response;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class NewsDtoResponse extends RepresentationModel<NewsDtoResponse> {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}



