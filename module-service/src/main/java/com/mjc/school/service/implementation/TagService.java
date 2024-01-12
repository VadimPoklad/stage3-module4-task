package com.mjc.school.service.implementation;

import com.mjc.school.repository.implementation.TagRepository;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.exceptions.ModelNotFoundException;
import com.mjc.school.service.implementation.dto.request.TagDtoRequest;
import com.mjc.school.service.implementation.dto.response.TagDtoResponse;
import com.mjc.school.service.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TagService implements BaseService<TagDtoResponse, TagDtoRequest, Long> {
    private final TagRepository repository;
    private final Mapper<Tag, TagDtoRequest, TagDtoResponse> mapper;

    @Autowired
    public TagService(TagRepository repository, Mapper<Tag, TagDtoRequest, TagDtoResponse>  mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TagDtoResponse create(TagDtoRequest requestDto) {
        Tag createdModel = repository.create(mapper.dtoToModel(requestDto));
        return mapper.modelToDto(createdModel);
    }

    @Override
    public List<TagDtoResponse> getAll(PageRequest pageRequest) {
        List<Tag> list = repository.readAll(pageRequest);
        return mapper.modelListToDtoList(list);

    }

    @Override
    public TagDtoResponse getById(Long id) {
        Optional<Tag> tag = repository.readById(id);
        if(tag.isEmpty()) throw new ModelNotFoundException();
        return mapper.modelToDto(tag.get());

    }
    public List<TagDtoResponse> getAllByNewsId(Long id) {
        try {
            return mapper.modelListToDtoList(repository.readByNewsId(id));
        }catch (NullPointerException e){
            throw new ModelNotFoundException();
        }
    }
    @Override
    public TagDtoResponse updateById(Long id, TagDtoRequest requestDto) {
        Tag updatingTag = mapper.dtoToModel(requestDto);
        updatingTag.setId(id);
        return mapper.modelToDto(repository.update(updatingTag));
    }

    @Override
    public TagDtoResponse patchById(Long id, TagDtoRequest requestDto) {
        Tag patchingTag = mapper.dtoToModel(requestDto);
        patchingTag.setId(id);
        return mapper.modelToDto(repository.patch(patchingTag));
    }

    @Override
    public boolean removeById(Long id) {
        boolean deleted = repository.deleteById(id);
        if(!deleted) throw new ModelNotFoundException();
        return true;
    }
}
