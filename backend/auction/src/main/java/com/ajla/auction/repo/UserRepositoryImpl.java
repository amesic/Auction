package com.ajla.auction.repo;

import com.ajla.auction.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
    //properties
    final EntityManager em;

    //dependency injection
    @Autowired
    public UserRepositoryImpl(final EntityManager em){
        Objects.requireNonNull(em, "userService must not be null.");
        this.em = em;
    }

   /* @Override
    //find me user with parameter email in database if exist
    public User findByEmail(final String email) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        //describes what we want to do in the query. Also, it declares the type of a row in the result
        final CriteriaQuery<User> cq = cb.createQuery(User.class);

        final Root<User> user = cq.from(User.class);
        //we create predicates against our User entity. Note, that these predicates don't have any effect yet
        final Predicate userEmailPredicate = cb.equal(user.get("email"), email);
        //apply predicates to our CriteriaQuery
        cq.where(userEmailPredicate);
        final TypedQuery<User> query = em.createQuery(cq);

        if (query.getSingleResult() == null) {
            return null;
        }
        return  query.getSingleResult();
    }*/

}
