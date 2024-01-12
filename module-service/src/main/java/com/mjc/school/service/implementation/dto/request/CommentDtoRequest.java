package com.mjc.school.service.implementation.dto.request;


import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDtoRequest {
    @Size(min = 3, message = "{validation.content.size.too_short}")
    @Size(max = 255, message = "{validation.content.size.too_long}")
    protected String content;
    @Min(1)
    protected Long newsId;
}
