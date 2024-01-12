package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Repository

public class AuthorRepository implements BaseRepository<Author, Long> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Author> readAll(PageRequest pageRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> root = criteriaQuery.from(Author.class);

        List<Order> orders = pageRequest.getSort().stream()
                .map(s -> criteriaBuilder.asc(root.get(s.getProperty()))).toList();

        criteriaQuery.orderBy(orders);
        return entityManager
                .createQuery(criteriaQuery.select(root))
                .setFirstResult(pageRequest.getPageSize() * pageRequest.getPageNumber())
                .setMaxResults(pageRequest.getPageSize())
                .getResultList();
    }

    @Override
    public Optional<Author> readById(Long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    public Author readByNewsId(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> root = criteriaQuery.from(Author.class);
        Join<Author, News> join = root.join("news");
        criteriaQuery.where(criteriaBuilder.equal(join.get("id"), id));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public Author create(Author entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Author update(Author entity) {
        Author author = entityManager.find(Author.class, entity.getId());
        author.setName(entity.getName());
        return author;
    }

    @Override
    public Author patch(Author entity) {
        Author author = entityManager.find(Author.class, entity.getId());
        if(entity.getName()!= null) author.setName(entity.getName());
        return author;
    }

    @Override
    public boolean deleteById(Long id) {
        Author author = entityManager.find(Author.class, id);
        if (author != null) {
            entityManager.remove(author);
            return true;
        }
        return false;
    }
}
