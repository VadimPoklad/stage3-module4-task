package com.mjc.school.service.implementation.dto.request.full;

import com.mjc.school.service.implementation.dto.request.CommentDtoRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDtoFullRequest extends CommentDtoRequest {
    @Size(min = 3, message = "{validation.content.size.too_short}")
    @Size(max = 255, message = "{validation.content.size.too_long}")
    @NotNull
    protected String content;
    @Min(1)
    @NotNull
    protected Long newsId;
}
