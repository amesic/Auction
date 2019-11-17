package com.ajla.auction.repo;

import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    final EntityManager em;

    @Autowired
    public ProductRepositoryImpl(final EntityManager em) {
        Objects.requireNonNull(em, "em must not be null.");
        this.em = em;
    }

    @Override
    public List<Product> getAllFeatureProducts() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        final Root<Product> product = cq.from(Product.class);

        cq.where(cb.equal(product.get("feature"), true));

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
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        final Root<Product> product = cq.from(Product.class);

        cq.where(cb.equal(product.get("feature"), false));

        final TypedQuery<Product> query = em.createQuery(cq);
        query.setMaxResults(3);

        if (query.getResultList().isEmpty()) {
            return null;
        }
        return  query.getResultList();
    }
    @Override
    public PaginationInfo<Product> getAllLastChanceProducts(final int pageNumber, final int size) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        final Root<Product> product = cq.from(Product.class);

        cq.where(cb.or(
                cb.equal(product.get("endDate"), LocalDate.now()),
                cb.equal(product.get("endDate"), LocalDate.now().plusDays(1))
        ))
                .orderBy( cb.asc(product.get("endDate")));
        final TypedQuery<Product> query = em.createQuery(cq);
        if (query.getResultList().isEmpty()) {
            return null;
        }
        //set total number of last chance products
        final int totalNumberOfItems = query.getResultList().size();
        //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
        query.setFirstResult(pageNumber * size);
        query.setMaxResults(size);

        PaginationInfo<Product> paginationInfo = new PaginationInfo<>(size, pageNumber, totalNumberOfItems, query.getResultList());
        return  paginationInfo;
    }
    @Override
    public PaginationInfo<Product> getAllNewArrivalProducts(final int pageNumber, final int size) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        final Root<Product> product = cq.from(Product.class);

        cq.where(cb.and(
                cb.greaterThan(product.get("endDate"), LocalDate.now()),
                cb.lessThanOrEqualTo(product.get("startDate"), LocalDate.now())
        ))
                .orderBy( cb.desc(product.get("startDate")));

        final TypedQuery<Product> query = em.createQuery(cq);
        if (query.getResultList().isEmpty()) {
            return null;
        }
        //set total number of last chance products
        final int totalNumberOfItems = query.getResultList().size();
        //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
        query.setFirstResult(pageNumber * size);
        query.setMaxResults(size);

        PaginationInfo<Product> paginationInfo = new PaginationInfo<>(size, pageNumber, totalNumberOfItems, query.getResultList());
        return  paginationInfo;
    }
    @Override
    public Long getSubcategoryIdOfProduct (final Long idProduct) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        final Root<Product> product = cq.from(Product.class);

        cq.where(cb.equal(product.get("id"), idProduct));
        final TypedQuery<Product> query = em.createQuery(cq);

        return query.getSingleResult().getSubcategory().getId();
    }
    @Override
    public List<Product> getRelatedProducts(final Long idProduct) {
        final Long idSubcategory = getSubcategoryIdOfProduct(idProduct);
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        final Root<Product> product = cq.from(Product.class);

        cq.where(cb.and(cb.equal(product.get("subcategory"), idSubcategory),
                cb.notEqual(product.get("id"), idProduct),
                cb.greaterThan(product.get("endDate"), LocalDate.now()),
                cb.lessThanOrEqualTo(product.get("startDate"), LocalDate.now())
        ))
                .orderBy(cb.desc(product.get("startDate")));

        final TypedQuery<Product> query = em.createQuery(cq);
        query.setMaxResults(3);
        if (query.getResultList().isEmpty()) {
            return null;
        }
        return query.getResultList();
    }
}
