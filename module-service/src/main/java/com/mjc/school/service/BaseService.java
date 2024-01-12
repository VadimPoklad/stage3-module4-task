package com.mjc.school.service;

import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BaseService<S, Q, K> {
    S create(Q dto);
    List<S> getAll(PageRequest pageRequest);
    S getById(K id);
    S updateById(K id, Q dto);
    S patchById(K id, Q dto);
    boolean removeById(K id);
}
