package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepository implements BaseRepository<Tag, Long> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> readAll(PageRequest pageRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);

        List<Order> orders = pageRequest.getSort().stream()
                .map(s -> criteriaBuilder.asc(root.get(s.getProperty()))).toList();

        criteriaQuery.orderBy(orders);
        return entityManager
                .createQuery(criteriaQuery.select(root))
                .setFirstResult(pageRequest.getPageSize() * pageRequest.getPageNumber())
                .setMaxResults(pageRequest.getPageSize())
                .getResultList();
    }

    public List<Tag> readByNewsId(Long id) {
        return entityManager.find(News.class, id).getTags();
    }

    public List<Tag> readAllByIds(List<Long> ids) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root);
        criteriaQuery.where(root.get("id").in(ids));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<Tag> readById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Tag create(Tag entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Tag update(Tag entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Tag patch(Tag entity) {
        Tag tag = entityManager.find(Tag.class, entity.getId());
        if(entity.getName()!= null) tag.setName(entity.getName());
        return tag;
    }

    @Override
    public boolean deleteById(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag != null) {
            entityManager.remove(tag);
            return true;
        }
        return false;
    }
}
