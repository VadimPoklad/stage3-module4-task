package com.mjc.school.service.implementation;

import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.implementation.TagRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.exceptions.IllegalDependencyException;
import com.mjc.school.service.exceptions.ModelNotFoundException;
import com.mjc.school.service.implementation.dto.response.NewsDtoResponse;
import com.mjc.school.service.implementation.dto.request.NewsDtoRequest;
import com.mjc.school.service.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NewsService implements BaseService<NewsDtoResponse, NewsDtoRequest, Long> {
    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final Mapper<News, NewsDtoRequest, NewsDtoResponse> mapper;

    @Autowired
    public NewsService(NewsRepository newsRepository,
                       AuthorRepository authorRepository,
                       TagRepository tagRepository,
                       Mapper<News, NewsDtoRequest, NewsDtoResponse> mapper) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest dto) {
        Optional<Author> author = authorRepository.readById(dto.getAuthorId());
        if (author.isEmpty()) throw new IllegalDependencyException();

        List<Tag> tags = tagRepository.readAllByIds(dto.getTagIds());
        if (dto.getTagIds().size() != tags.size()) throw new IllegalDependencyException();

        News news = News.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(author.get())
                .tags(tags)
                .build();

        return mapper.modelToDto(newsRepository.create(news));
    }

    @Override
    public List<NewsDtoResponse> getAll(PageRequest pageRequest) {
        return mapper.modelListToDtoList(newsRepository.readAll(pageRequest));
    }

    @Override
    public NewsDtoResponse getById(Long id) {
        Optional<News> news = newsRepository.readById(id);
        if (news.isEmpty()) {
            throw new ModelNotFoundException();
        }
        return mapper.modelToDto(news.get());
    }
    @Override
    public NewsDtoResponse updateById(Long id, NewsDtoRequest dto) {
        Optional<Author> author = authorRepository.readById(dto.getAuthorId());
        if (author.isEmpty()) throw new IllegalDependencyException();

        List<Tag> tags = tagRepository.readAllByIds(dto.getTagIds());
        if (dto.getTagIds().size() != tags.size()) throw new IllegalDependencyException();

        News updatingNews = mapper.dtoToModel(dto);
        updatingNews.setId(id);
        updatingNews.setAuthor(author.get());
        updatingNews.setTags(tags);

        return mapper.modelToDto(newsRepository.update(updatingNews));
    }

    @Override
    public NewsDtoResponse patchById(Long id, NewsDtoRequest dto) {
        Optional<Author> author = Optional.empty();
        if (dto.getAuthorId() != null) {
            author = authorRepository.readById(dto.getAuthorId());
            if (author.isEmpty()) throw new IllegalDependencyException();
        }

        List<Tag> tags = null;
        if (dto.getTagIds() != null) {
            tags = tagRepository.readAllByIds(dto.getTagIds());
            if (dto.getTagIds().size() != tags.size()) throw new IllegalDependencyException();
        }

        News updatingNews = mapper.dtoToModel(dto);
        updatingNews.setId(id);
        updatingNews.setAuthor(author.orElse(null));
        updatingNews.setTags(tags);

        return mapper.modelToDto(newsRepository.patch(updatingNews));
    }

    @Override
    public boolean removeById(Long id) {
        boolean deleted = newsRepository.deleteById(id);
        if (!deleted) throw new ModelNotFoundException();
        return true;
    }
}
