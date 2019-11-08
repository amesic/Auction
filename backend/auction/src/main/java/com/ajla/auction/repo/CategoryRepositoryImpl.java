package com.ajla.auction.repo;

import com.ajla.auction.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {
    //properties
    final EntityManager em;

    //dependency injection
    @Autowired
    public CategoryRepositoryImpl(final EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Category> findExactNumberOfCategories(Long numberOfCategories) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        //describes what we want to do in the query. Also, it declares the type of a row in the result
        final CriteriaQuery<Category> cq = cb.createQuery(Category.class);

        final Root<Category> category = cq.from(Category.class);
        //we create predicates against our Category entity. Note, that these predicates don't have any effect yet
        //apply predicates to our CriteriaQuery
        final TypedQuery<Category> query = em.createQuery(cq);
        query.setMaxResults(Math.toIntExact(numberOfCategories));
        if (query.getResultList().isEmpty()) {
            return null;
        }
        return  query.getResultList();
    }
}
