package com.ajla.auction.repo;

import com.ajla.auction.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    //properties
    final EntityManager em;

    //dependency injection
    @Autowired
    public ProductRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Product> getAllFeatureProducts() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        //describes what we want to do in the query. Also, it declares the type of a row in the result
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        final Root<Product> product = cq.from(Product.class);
        //we create predicates against our Category entity. Note, that these predicates don't have any effect yet
        //get categoryParentId where that is null
        final Predicate feature = cb.equal(product.get("feature"), true);
        //apply predicates to our CriteriaQuery
        cq.where(feature);
        final TypedQuery<Product> query = em.createQuery(cq);
        query.setMaxResults(4);

        if (query.getResultList().isEmpty()) {
            return null;
        }
        return  query.getResultList();
    }
    @Override
    public List<Product> getAllFeatureCollection() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        //describes what we want to do in the query. Also, it declares the type of a row in the result
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        final Root<Product> product = cq.from(Product.class);
        //we create predicates against our Category entity. Note, that these predicates don't have any effect yet
        //get categoryParentId where that is null
        final Predicate feature = cb.equal(product.get("feature"), false);
        //apply predicates to our CriteriaQuery
        cq.where(feature);
        final TypedQuery<Product> query = em.createQuery(cq);
        query.setMaxResults(3);

        if (query.getResultList().isEmpty()) {
            return null;
        }
        return  query.getResultList();
    }
    @Override
    public List<Product> getAllLastChanceProducts(int page, int size) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        //describes what we want to do in the query. Also, it declares the type of a row in the result
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        final Root<Product> product = cq.from(Product.class);
        //we create predicates against our Category entity. Note, that these predicates don't have any effect yet
        //get categoryParentId where that is null
        final LocalDate now = LocalDate.now();
        final Predicate endDateProductsNow = cb.equal(product.get("endDate"), now);
        final LocalDate tomorrow = now.plusDays(1);
        final Predicate endDateProductsTomorrow = cb.equal(product.get("endDate"), tomorrow);
        final Predicate endDateProducts = cb.or(endDateProductsNow, endDateProductsTomorrow);
        //apply predicates to our CriteriaQuery
        cq.where(endDateProducts).orderBy( cb.asc(product.get("endDate")));;
        final TypedQuery<Product> query = em.createQuery(cq);
        if (query.getResultList().isEmpty()) {
            return null;
        }
        //page starts from 0, return list of size number element, starting from page*size index of element
        query.setFirstResult(page * size);
        //if there is no elements left in list return null
        if(query.getResultList().isEmpty()) {
            return null;
        }
        query.setMaxResults(size);
        return  query.getResultList();
    }
    @Override
    public List<Product> getAllNewArrivalProducts(int page, int size) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        //describes what we want to do in the query. Also, it declares the type of a row in the result
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        final Root<Product> product = cq.from(Product.class);
        //we create predicates against our Category entity. Note, that these predicates don't have any effect yet
        //get categoryParentId where that is null
        LocalDate now = LocalDate.now();
        final Predicate currentProducts = cb.greaterThan(product.get("endDate"), now);
        //apply predicates to our CriteriaQuery
        final Predicate currentProducts1 = cb.lessThanOrEqualTo(product.get("startDate"), now);
        final Predicate newArrivalsProduct = cb.and(currentProducts, currentProducts1);
        cq.where(newArrivalsProduct).orderBy( cb.desc(product.get("startDate")));
        final TypedQuery<Product> query = em.createQuery(cq);
        if (query.getResultList().isEmpty()) {
            return null;
        }
        //page starts from 0, return list of size number element, starting from page*size index of element
        query.setFirstResult(page * size);
        //if there is no elements left in list return null
        if(query.getResultList().isEmpty()) {
            return null;
        }
        query.setMaxResults(size);
        return  query.getResultList();


    }
}
