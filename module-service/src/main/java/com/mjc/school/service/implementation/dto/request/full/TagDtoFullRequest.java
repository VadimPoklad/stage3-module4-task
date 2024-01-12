package com.mjc.school.service.implementation.dto.request.full;

import com.mjc.school.service.implementation.dto.request.TagDtoRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDtoFullRequest extends TagDtoRequest {
    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 15, message = "{validation.name.size.too_long}")
    @NotNull
    private String name;
}
