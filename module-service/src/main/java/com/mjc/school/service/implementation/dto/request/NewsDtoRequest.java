package com.mjc.school.service.implementation.dto.request;


import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDtoRequest {
    @Size(min = 5, message = "{validation.title.size.too_short}")
    @Size(max = 30, message = "{validation.title.size.too_long}")
    protected String title;
    @Size(min = 5, message = "{validation.content.size.too_short}")
    @Size(max = 255, message = "{validation.content.size.too_long}")
    protected String content;
    protected Long authorId;
    protected List<Long> tagIds;
}
