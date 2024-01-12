package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.implementation.AuthorService;
import com.mjc.school.service.implementation.dto.request.AuthorDtoRequest;
import com.mjc.school.service.implementation.dto.request.full.AuthorDtoFullRequest;
import com.mjc.school.service.implementation.dto.response.AuthorDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Validated
@RestController
@RequestMapping(value = "/authors", produces = "application/json")
public class AuthorRestController implements BaseController<AuthorDtoRequest, AuthorDtoFullRequest, AuthorDtoResponse, Long> {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<AuthorDtoResponse> getAll(
            @Min(0) @RequestParam(required = false, defaultValue = "0") int page,
            @Min(1) @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sortedBy
    ) {
        CollectionModel<AuthorDtoResponse> collectionModel =
                CollectionModel.of(authorService.getAll(
                        PageRequest.of(page, size, Sort.by(sortedBy))));
        collectionModel.forEach(dto ->
                dto.add(linkTo(AuthorRestController.class).slash(dto.getId()).withSelfRel()));

        collectionModel.add(linkTo(AuthorRestController.class).withSelfRel());
        collectionModel.add(linkTo(methodOn(AuthorRestController.class)
                .getAll(page, size, sortedBy)).withRel("page"));
        return collectionModel;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse getById(@PathVariable Long id) {
        return authorService.getById(id)
                .add(linkTo(AuthorRestController.class).slash(id).withSelfRel());
    }

    @GetMapping(value = "/news/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse getByNewsId(@PathVariable Long id) {
        return authorService.getByNewsId(id)
                .add(linkTo(methodOn(AuthorRestController.class).getByNewsId(id)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDtoResponse create(@Valid @RequestBody AuthorDtoFullRequest dtoFullRequest) {
        AuthorDtoResponse response = authorService.create(dtoFullRequest);
        return response
                .add(linkTo(AuthorRestController.class).slash(response.getId()).withSelfRel());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse update(@Valid @RequestBody AuthorDtoFullRequest dtoFullRequest, @PathVariable Long id) {
        return authorService.updateById(id, dtoFullRequest)
                .add(linkTo(AuthorRestController.class).slash(id).withSelfRel());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse patch(@Valid @RequestBody AuthorDtoRequest dtoRequest, @PathVariable Long id) {
        return authorService.patchById(id, dtoRequest)
                .add(linkTo(AuthorRestController.class).slash(id).withSelfRel());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        authorService.removeById(id);
    }
}
