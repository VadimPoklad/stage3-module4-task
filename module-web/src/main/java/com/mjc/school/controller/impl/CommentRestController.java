package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.implementation.CommentService;
import com.mjc.school.service.implementation.dto.request.full.CommentDtoFullRequest;
import com.mjc.school.service.implementation.dto.request.CommentDtoRequest;
import com.mjc.school.service.implementation.dto.response.CommentDtoResponse;
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
@RequestMapping(value = "/comments", produces = "application/json")
public class CommentRestController implements BaseController<CommentDtoRequest,
        CommentDtoFullRequest, CommentDtoResponse, Long> {
    @Autowired
    private CommentService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<CommentDtoResponse> getAll(
            @Min(0) @RequestParam(required = false, defaultValue = "0") int page,
            @Min(1) @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sortedBy) {
        CollectionModel<CommentDtoResponse> collectionModel = CollectionModel.of(service.getAll(PageRequest.of(page, size, Sort.by(sortedBy))));

        collectionModel.forEach(dto ->
                dto.add(linkTo(CommentRestController.class).slash(dto.getId()).withSelfRel())
        );

        collectionModel.add(linkTo(CommentRestController.class).withSelfRel());
        collectionModel.add(linkTo(methodOn(CommentRestController.class)
                .getAll(page, size, sortedBy)).withRel("page"));
        return collectionModel;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDtoResponse getById(@PathVariable Long id) {
        CommentDtoResponse commentDtoResponse = service.getById(id);
        return commentDtoResponse
                .add(linkTo(CommentRestController.class).slash(id).withSelfRel());
    }


    @GetMapping(value = "/news/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<CommentDtoResponse> getAllByNewsId(@PathVariable Long id) {
        CollectionModel<CommentDtoResponse> collectionModel = CollectionModel.of(service.getAllByNewsId(id));

        collectionModel.forEach(dto ->
                dto.add(linkTo(CommentRestController.class).slash(dto.getId()).withSelfRel())
        );

        collectionModel.add(linkTo(methodOn(CommentRestController.class).getAllByNewsId(id)).withSelfRel());
        return collectionModel;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDtoResponse create(@Valid @RequestBody CommentDtoFullRequest fullDtoRequest) {
        CommentDtoResponse createdComment = service.create(fullDtoRequest);
        return createdComment
                .add(linkTo(CommentRestController.class).slash(createdComment.getId()).withSelfRel());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDtoResponse update(@Valid @RequestBody CommentDtoFullRequest fullDtoRequest, @PathVariable Long id) {
        return service.updateById(id, fullDtoRequest)
                .add(linkTo(CommentRestController.class).slash(id).withSelfRel());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDtoResponse patch(@Valid @RequestBody CommentDtoRequest dtoRequest, @PathVariable Long id) {
        return service.patchById(id, dtoRequest)
                .add(linkTo(CommentRestController.class).slash(id).withSelfRel());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }
}