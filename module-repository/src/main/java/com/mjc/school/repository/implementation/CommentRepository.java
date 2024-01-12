package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.repository.model.News;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository implements BaseRepository<Comment, Long> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Comment> readAll(PageRequest pageRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> root = criteriaQuery.from(Comment.class);

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
    public Optional<Comment> readById(Long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    public List<Comment> readByNewsId(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> root = criteriaQuery.from(Comment.class);
        Join<Comment, News> join = root.join("news");
        criteriaQuery.where(criteriaBuilder.equal(join.get("id"), id));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Comment create(Comment entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Comment update(Comment entity) {
        Comment comment = entityManager.find(Comment.class, entity.getId());
        comment.setContent(entity.getContent());
        comment.setNews(entity.getNews());
        return comment;
    }

    @Override
    public Comment patch(Comment entity) {
        Comment comment = entityManager.find(Comment.class, entity.getId());
        if(entity.getContent()!= null) comment.setContent(entity.getContent());
        if(entity.getNews()!= null) comment.setNews(entity.getNews());
        return comment;
    }

    @Override
    public boolean deleteById(Long id) {
        Comment comment = entityManager.find(Comment.class, id);
        if (comment != null) {
            entityManager.remove(comment);
            return true;
        }
        return false;
    }
}
