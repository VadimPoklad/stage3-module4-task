package com.mjc.school.service.implementation.dto.request;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDtoRequest {
    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 15, message = "{validation.name.size.too_long}")
    protected String name;
}
