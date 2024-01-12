package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.implementation.dto.request.full.NewsDtoFullRequest;
import com.mjc.school.service.implementation.dto.response.NewsDtoResponse;
import com.mjc.school.service.implementation.NewsService;
import com.mjc.school.service.implementation.dto.request.NewsDtoRequest;
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
@RequestMapping(value = "/news", produces = "application/json")
public class NewsRestController implements BaseController<NewsDtoRequest,
        NewsDtoFullRequest, NewsDtoResponse, Long> {
    @Autowired
    private NewsService newsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<NewsDtoResponse> getAll(
            @Min(0) @RequestParam(required = false, defaultValue = "0") int page,
            @Min(1) @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sortedBy) {
        CollectionModel<NewsDtoResponse> collectionModel = CollectionModel.of(newsService.getAll(PageRequest.of(page, size, Sort.by(sortedBy))));

        for (NewsDtoResponse news : collectionModel) {
            news
                    .add(linkTo(methodOn(NewsRestController.class).getById(news.getId())).withSelfRel())
                    .add(linkTo(methodOn(AuthorRestController.class).getByNewsId(news.getId())).withRel("author"))
                    .add(linkTo(methodOn(TagRestController.class).getAllByNewsId(news.getId())).withRel("tags"))
                    .add(linkTo(methodOn(CommentRestController.class).getAllByNewsId(news.getId())).withRel("comments"));
        }
        collectionModel.add(linkTo(NewsRestController.class).withSelfRel());
        collectionModel.add(linkTo(methodOn(NewsRestController.class)
                .getAll(page, size, sortedBy)).withRel("page"));
        return collectionModel;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDtoResponse getById(@PathVariable Long id) {
        NewsDtoResponse newsDtoResponse = newsService.getById(id);
        newsDtoResponse
                .add(linkTo(methodOn(NewsRestController.class).getById(id)).withSelfRel())
                .add(linkTo(methodOn(AuthorRestController.class).getByNewsId(id)).withRel("author"))
                .add(linkTo(methodOn(TagRestController.class).getAllByNewsId(id)).withRel("tags"))
                .add(linkTo(methodOn(CommentRestController.class).getAllByNewsId(id)).withRel("comments"));
        return newsDtoResponse;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDtoResponse create(@Valid @RequestBody NewsDtoFullRequest dtoRequest) {
        NewsDtoResponse news = newsService.create(dtoRequest);
        news
                .add(linkTo(methodOn(NewsRestController.class).getById(news.getId())).withSelfRel())
                .add(linkTo(methodOn(AuthorRestController.class).getByNewsId(news.getId())).withRel("author"))
                .add(linkTo(methodOn(TagRestController.class).getAllByNewsId(news.getId())).withRel("tags"))
                .add(linkTo(methodOn(CommentRestController.class).getAllByNewsId(news.getId())).withRel("comments"));
        return news;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDtoResponse update(@Valid @RequestBody NewsDtoFullRequest dtoRequest, @PathVariable Long id) {
        NewsDtoResponse news = newsService.updateById(id, dtoRequest);
        news
                .add(linkTo(methodOn(NewsRestController.class).getById(id)).withSelfRel())
                .add(linkTo(methodOn(AuthorRestController.class).getByNewsId(news.getId())).withRel("author"))
                .add(linkTo(methodOn(TagRestController.class).getAllByNewsId(news.getId())).withRel("tags"))
                .add(linkTo(methodOn(CommentRestController.class).getAllByNewsId(news.getId())).withRel("comments"));
        return news;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDtoResponse patch(@Valid @RequestBody NewsDtoRequest dtoRequest, @PathVariable Long id) {
        NewsDtoResponse news = newsService.patchById(id, dtoRequest);
        news
                .add(linkTo(methodOn(NewsRestController.class).getById(id)).withSelfRel())
                .add(linkTo(methodOn(AuthorRestController.class).getByNewsId(id)).withRel("author"))
                .add(linkTo(methodOn(TagRestController.class).getAllByNewsId(id)).withRel("tags"))
                .add(linkTo(methodOn(CommentRestController.class).getAllByNewsId(id)).withRel("comments"));
        return news;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        newsService.removeById(id);
    }
}