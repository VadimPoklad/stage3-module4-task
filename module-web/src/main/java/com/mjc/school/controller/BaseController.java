package com.mjc.school.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;


public interface BaseController<Q, F, S, T> {
    CollectionModel<S> getAll(
            @Min(0) @RequestParam(required = false, defaultValue = "0") int page,
            @Min(1) @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sortedBy);
    S getById(@PathVariable T id);
    S create(@Valid @RequestBody F fullDtoRequest);
    S update(@Valid @RequestBody F dtoRequest, @PathVariable T id);
    S patch(@Valid @RequestBody Q dtoRequest, @PathVariable T id);
    void delete(@PathVariable T id);
}
