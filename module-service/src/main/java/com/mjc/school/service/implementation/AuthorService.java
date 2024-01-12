package com.mjc.school.service.implementation;

import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.exceptions.ModelNotFoundException;
import com.mjc.school.service.implementation.dto.request.AuthorDtoRequest;
import com.mjc.school.service.implementation.dto.response.AuthorDtoResponse;

import com.mjc.school.service.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService implements BaseService<AuthorDtoResponse, AuthorDtoRequest, Long> {
    private final AuthorRepository repository;
    private final Mapper<Author, AuthorDtoRequest, AuthorDtoResponse> mapper;

    @Autowired
    public AuthorService(AuthorRepository repository, Mapper<Author, AuthorDtoRequest, AuthorDtoResponse> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AuthorDtoResponse create(AuthorDtoRequest dto) {
        Author createdModel = repository.create(mapper.dtoToModel(dto));
        return mapper.modelToDto(createdModel);

    }

    @Override
    public List<AuthorDtoResponse> getAll(PageRequest pageRequest) {
        return mapper.modelListToDtoList(repository.readAll(pageRequest));

    }

    @Override
    public AuthorDtoResponse getById(Long id) {
        Optional<Author> model = repository.readById(id);
        if (model.isEmpty()) {
            throw new ModelNotFoundException();
        }
        return mapper.modelToDto(model.get());

    }

    public AuthorDtoResponse getByNewsId(Long id) {
        return mapper.modelToDto(repository.readByNewsId(id));
    }

    @Override
    public AuthorDtoResponse updateById(Long id, AuthorDtoRequest requestDto) {
        Author author = mapper.dtoToModel(requestDto);
        author.setId(id);
        return mapper.modelToDto(repository.update(author));

    }

    @Override
    public AuthorDtoResponse patchById(Long id, AuthorDtoRequest requestDto) {
        Author author = mapper.dtoToModel(requestDto);
        author.setId(id);
        return mapper.modelToDto(repository.patch(author));
    }

    @Override
    public boolean removeById(Long id) {
        boolean deleted = repository.deleteById(id);
        if (!deleted) throw new ModelNotFoundException();
        return true;
    }

}
