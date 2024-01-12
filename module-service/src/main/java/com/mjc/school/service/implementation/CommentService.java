package com.mjc.school.service.implementation;


import com.mjc.school.repository.implementation.CommentRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.exceptions.IllegalDependencyException;
import com.mjc.school.service.exceptions.ModelNotFoundException;
import com.mjc.school.service.implementation.dto.response.CommentDtoResponse;
import com.mjc.school.service.implementation.dto.request.CommentDtoRequest;

import com.mjc.school.service.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService implements BaseService<CommentDtoResponse, CommentDtoRequest, Long> {
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final Mapper<Comment, CommentDtoRequest, CommentDtoResponse> mapper;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          NewsRepository newsRepository,
                          Mapper<Comment, CommentDtoRequest, CommentDtoResponse> mapper) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDtoResponse create(CommentDtoRequest requestDto) {
        Optional<News> news = newsRepository.readById(requestDto.getNewsId());
        if (news.isEmpty()) throw new IllegalDependencyException();

        Comment comment = mapper.dtoToModel(requestDto);
        comment.setNews(news.get());

        return mapper.modelToDto(commentRepository.create(comment));
    }

    @Override
    public List<CommentDtoResponse> getAll(PageRequest pageRequest) {
        return mapper.modelListToDtoList(commentRepository.readAll(pageRequest));

    }

    @Override
    public CommentDtoResponse getById(Long id) {
        Optional<Comment> tag = commentRepository.readById(id);
        if (tag.isEmpty()) throw new ModelNotFoundException();
        return mapper.modelToDto(tag.get());

    }

    public List<CommentDtoResponse> getAllByNewsId(Long id) {
        return mapper.modelListToDtoList(commentRepository.readByNewsId(id));
    }

    @Override
    public CommentDtoResponse updateById(Long id, CommentDtoRequest requestDto) {
        Optional<News> news = newsRepository.readById(requestDto.getNewsId());
        if (news.isEmpty()) throw new IllegalDependencyException();

        Comment comment = mapper.dtoToModel(requestDto);
        comment.setId(id);
        comment.setNews(news.get());

        return mapper.modelToDto(commentRepository.update(comment));
    }

    @Override
    public CommentDtoResponse patchById(Long id, CommentDtoRequest requestDto) {
        Optional<News> news = Optional.empty();
        if (requestDto.getNewsId() != null) {
            news = newsRepository.readById(requestDto.getNewsId());
            if (news.isEmpty()) throw new IllegalDependencyException();
        }

        Comment comment = mapper.dtoToModel(requestDto);
        comment.setId(id);
        comment.setNews(news.orElse(null));

        return mapper.modelToDto(commentRepository.patch(comment));
    }

    @Override
    public boolean removeById(Long id) {
        boolean deleted = commentRepository.deleteById(id);
        if (!deleted) throw new ModelNotFoundException();
        return true;
    }
}