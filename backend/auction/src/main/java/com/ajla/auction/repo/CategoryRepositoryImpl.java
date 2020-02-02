package com.ajla.auction.repo;

import com.ajla.auction.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {
    private final EntityManager em;

    @Autowired
    public CategoryRepositoryImpl(final EntityManager em) {
        Objects.requireNonNull(em, "em must not be null.");
        this.em = em;
    }

    @Override
    public List<Category> findExactNumberOfCategories(final Long numberOfCategories) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Category> cq = cb.createQuery(Category.class);
        final Root<Category> category = cq.from(Category.class);

        final TypedQuery<Category> query = em.createQuery(cq);
        query.setMaxResults(Math.toIntExact(numberOfCategories));
        if (query.getResultList().isEmpty()) {
            return null;
        }
        return  query.getResultList();
    }
}
