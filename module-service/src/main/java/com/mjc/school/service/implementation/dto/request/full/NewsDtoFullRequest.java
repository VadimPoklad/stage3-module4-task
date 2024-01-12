package com.mjc.school.service.implementation.dto.request.full;

import com.mjc.school.service.implementation.dto.request.NewsDtoRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDtoFullRequest extends NewsDtoRequest {
    @NotNull
    @Size(min = 5, message = "{validation.title.size.too_short}")
    @Size(max = 30, message = "{validation.title.size.too_long}")
    protected String title;
    @Size(min = 5, message = "{validation.content.size.too_short}")
    @Size(max = 255, message = "{validation.content.size.too_long}")
    @NotNull
    protected String content;
    @NotNull
    protected Long authorId;
    @NotNull
    protected List<Long> tagIds;
}
