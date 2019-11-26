package com.ajla.auction.repo;

import com.ajla.auction.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.*;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    final EntityManager em;
    final CategoryRepository categoryRepository;

    @Autowired
    public ProductRepositoryImpl(final EntityManager em, final CategoryRepository categoryRepository) {
        Objects.requireNonNull(em, "em must not be null.");
        Objects.requireNonNull(categoryRepository, "categoryRepository must not be null.");
        this.categoryRepository = categoryRepository;
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
    public PaginationInfo<Product> getAllLastChanceProducts(final Long pageNumber, final Long size) {
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
        final Long totalNumberOfItems = new Long(query.getResultList().size());
        //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
        query.setFirstResult(Math.toIntExact(pageNumber * size));
        query.setMaxResults(Math.toIntExact(size));

        PaginationInfo<Product> paginationInfo = new PaginationInfo<>(size, pageNumber, totalNumberOfItems, query.getResultList());
        return  paginationInfo;
    }
    @Override
    public PaginationInfo<Product> getAllNewArrivalProducts(final Long pageNumber, final Long size) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        final Root<Product> product = cq.from(Product.class);

        cq.where(cb.and(
                cb.greaterThanOrEqualTo(product.get("endDate"), LocalDate.now()),
                cb.lessThanOrEqualTo(product.get("startDate"), LocalDate.now())
        ))
                .orderBy( cb.desc(product.get("startDate")));

        final TypedQuery<Product> query = em.createQuery(cq);
        if (query.getResultList().isEmpty()) {
            return null;
        }
        //set total number of last chance products
        final Long totalNumberOfItems = new Long(query.getResultList().size());
        //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
        query.setFirstResult(Math.toIntExact(pageNumber * size));
        query.setMaxResults(Math.toIntExact(size));

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

        cq.where(cb.and(
                cb.equal(product.get("subcategory"), idSubcategory),
                cb.notEqual(product.get("id"), idProduct),
                cb.greaterThanOrEqualTo(product.get("endDate"), LocalDate.now()),
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
    @Override
    public Boolean userIsSellerOfProduct(final Long idUser, final Long idProduct) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        final Root<Product> product = cq.from(Product.class);

        cq.where(cb.and(
                cb.equal(product.get("id"), idProduct),
                cb.equal(product.get("seller"), idUser)));

        final TypedQuery<Product> query = em.createQuery(cq);
        if(query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }
    @Override
    public List<NumberOfProductsInfo> numberOfProductsBySubcategory(final List<Category> categories) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        final Root<Product> product = cq.from(Product.class);
        TypedQuery<Product> query;

        List<NumberOfProductsInfo> allCategories = new ArrayList<>();
        for (Category category: categories) {
            if(category.getSubcategories().size() != 0) {
                NumberOfProductsInfo categoryInfo = new NumberOfProductsInfo(category.getId(), category.getName(), Arrays.asList(), null);
                List<NumberOfProductsInfo> listOfSubcategoryInfo = new ArrayList<>();
                Long numberOfProductsByCategory = (long) 0;
                for (Category subcategory : category.getSubcategories()) {
                    cq.where(cb.equal(product.get("subcategory"), subcategory.getId()));
                    query = em.createQuery(cq);
                    NumberOfProductsInfo subcategoryInfo = new NumberOfProductsInfo(subcategory.getId(), subcategory.getName(), Arrays.asList(), (long) query.getResultList().size());
                    listOfSubcategoryInfo.add(subcategoryInfo);
                    numberOfProductsByCategory += (long) query.getResultList().size();
                }
                categoryInfo.setChildren(listOfSubcategoryInfo);
                categoryInfo.setNumberOfProducts(numberOfProductsByCategory);
                allCategories.add(categoryInfo);
            }
            continue;
        }
        return allCategories;
    }

    @Override
    public NumberOfProductsInfo numberOfProductsByCharacteristic(final Characteristic characteristic) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        final Root<Product> product = cq.from(Product.class);
        TypedQuery<Long> query;
        NumberOfProductsInfo mainCharacteristic = new NumberOfProductsInfo(characteristic.getId(), characteristic.getName(), Arrays.asList(), null);
        List<NumberOfProductsInfo> allCategoriesOfMainCharacteristic = new ArrayList<>();
        Long numberOfProductsBelongToMainCharacteristic = (long) 0;
        for (Characteristic oneOfCharacteristic: characteristic.getAllCharacteristic()) {
            query = em.createQuery("SELECT COUNT(p) FROM Characteristic c JOIN c.products p WHERE c.id =:characteristics_id", Long.class);
            query.setParameter("characteristics_id", oneOfCharacteristic.getId());
            NumberOfProductsInfo categoryOfCharacteristic = new NumberOfProductsInfo(oneOfCharacteristic.getId(),
                    oneOfCharacteristic.getName(), Arrays.asList(), query.getSingleResult());
            allCategoriesOfMainCharacteristic.add(categoryOfCharacteristic);
            numberOfProductsBelongToMainCharacteristic += query.getSingleResult();
        }
        mainCharacteristic.setChildren(allCategoriesOfMainCharacteristic);
        mainCharacteristic.setNumberOfProducts(numberOfProductsBelongToMainCharacteristic);
        return mainCharacteristic;
    }
    @Override
    public PaginationInfo<Product> getAllProductsBySort(final String typeOfSort,
                                                        final Long subcategoryId,
                                                        final Long filterColorId,
                                                        final Long filterSizeId,
                                                        final Long pageNumber,
                                                        final Long size) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        final Root<Product> product = cq.from(Product.class);

        final Category subcategory = categoryRepository.findCategoryById(subcategoryId);
        TypedQuery<Product> query;
        String sqlQuery ="SELECT p FROM Product p";
        query = em.createQuery(sqlQuery, Product.class);
        if (filterColorId != null && filterSizeId !=null) {
            sqlQuery += " WHERE p.id IN (SELECT p.id FROM Product p" +
                    " JOIN p.characteristics c WHERE c.id IN (:color_id, :size_id)" +
                    " GROUP BY p.id HAVING COUNT(c.id)=2)";
        } else if (filterColorId != null || filterSizeId !=null) {
            sqlQuery += " WHERE p.id IN (SELECT p.id FROM Product p" +
                    " JOIN p.characteristics c WHERE c.id =:filter_id)";
        }
        if (subcategoryId != null && (filterColorId != null || filterSizeId !=null)) {
            sqlQuery += " AND p.subcategory =:subcategory";
        } else if (subcategoryId != null) {
            sqlQuery += " WHERE p.subcategory =:subcategory";
        }


        if (typeOfSort.equals("default")) {
            if (subcategory == null && filterSizeId == null && filterColorId == null) {
                sqlQuery += " WHERE p.endDate >=:dateNow AND p.startDate <=:dateNow";
            } else {
                sqlQuery += " AND p.endDate >=:dateNow AND p.startDate <=:dateNow";
            }
                query = em.createQuery(sqlQuery, Product.class);
                if (filterColorId != null && filterSizeId !=null) {
                    query.setParameter("color_id", filterColorId);
                    query.setParameter("size_id", filterSizeId);
                } else if (filterColorId != null || filterSizeId !=null){
                    query.setParameter("filter_id", filterColorId);
                }
                if (subcategory != null) {
                    query.setParameter("subcategory", subcategory);
                }
                query.setParameter("dateNow", LocalDate.now());
                if (query.getResultList().isEmpty()) {
                    return null;
                }
                //set total number of last chance products
                final Long totalNumberOfItems = new Long(query.getResultList().size());
                //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
                query.setFirstResult(Math.toIntExact(pageNumber * size));
                query.setMaxResults(Math.toIntExact(size));
                PaginationInfo<Product> paginationInfo = new PaginationInfo<>(size, pageNumber, totalNumberOfItems, query.getResultList());
                return  paginationInfo;
        } else if (typeOfSort.equals("newness")) {
            if (subcategory == null && filterSizeId == null && filterColorId == null) {
                sqlQuery += " WHERE p.endDate >=:dateNow AND p.startDate <=:dateNow ORDER BY p.startDate DESC";
            } else {
                sqlQuery += " AND p.endDate >=:dateNow AND p.startDate <=:dateNow ORDER BY p.startDate DESC";
            }
            query = em.createQuery(sqlQuery, Product.class);
            if (filterColorId != null && filterSizeId !=null) {
                query.setParameter("color_id", filterColorId);
                query.setParameter("size_id", filterSizeId);
            } else if (filterColorId != null || filterSizeId !=null){
                query.setParameter("filter_id", filterColorId);
            }
            if (subcategory != null) {
                query.setParameter("subcategory", subcategory);
            }
            query.setParameter("dateNow", LocalDate.now());
            if (query.getResultList().isEmpty()) {
                return null;
            }
            //set total number of last chance products
            final Long totalNumberOfItems = new Long(query.getResultList().size());
            //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
            query.setFirstResult(Math.toIntExact(pageNumber * size));
            query.setMaxResults(Math.toIntExact(size));
            PaginationInfo<Product> paginationInfo = new PaginationInfo<>(size, pageNumber, totalNumberOfItems, query.getResultList());
            return  paginationInfo;
        } else if (typeOfSort.equals("priceLowest")) {
            if (subcategory == null && filterSizeId == null && filterColorId == null) {
                sqlQuery += " WHERE p.endDate >=:dateNow AND p.startDate <=:dateNow ORDER BY p.startPrice ASC";
            } else {
                sqlQuery += " AND p.endDate >=:dateNow AND p.startDate <=:dateNow ORDER BY p.startPrice ASC";
            }
            query = em.createQuery(sqlQuery, Product.class);
            if (filterColorId != null && filterSizeId !=null) {
                query.setParameter("color_id", filterColorId);
                query.setParameter("size_id", filterSizeId);
            } else if (filterColorId != null || filterSizeId !=null){
                query.setParameter("filter_id", filterColorId);
            }
            if (subcategory != null) {
                query.setParameter("subcategory", subcategory);
            }
            query.setParameter("dateNow", LocalDate.now());
            if (query.getResultList().isEmpty()) {
                return null;
            }
            //set total number of last chance products
            final Long totalNumberOfItems = new Long(query.getResultList().size());
            //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
            query.setFirstResult(Math.toIntExact(pageNumber * size));
            query.setMaxResults(Math.toIntExact(size));
            PaginationInfo<Product> paginationInfo = new PaginationInfo<>(size, pageNumber, totalNumberOfItems, query.getResultList());
            return  paginationInfo;
        } else if (typeOfSort.equals("priceHighest")) {
            if (subcategory == null && filterSizeId == null && filterColorId == null) {
                sqlQuery += " WHERE p.endDate >=:dateNow AND p.startDate <=:dateNow ORDER BY p.startPrice DESC";
            } else {
                sqlQuery += " AND p.endDate >=:dateNow AND p.startDate <=:dateNow ORDER BY p.startPrice DESC";
            }
            query = em.createQuery(sqlQuery, Product.class);
            if (filterColorId != null && filterSizeId !=null) {
                query.setParameter("color_id", filterColorId);
                query.setParameter("size_id", filterSizeId);
            } else if (filterColorId != null || filterSizeId !=null){
                query.setParameter("filter_id", filterColorId);
            }
            if (subcategory != null) {
                query.setParameter("subcategory", subcategory);
            }
            query.setParameter("dateNow", LocalDate.now());
            if (query.getResultList().isEmpty()) {
                return null;
            }
            //set total number of last chance products
            final Long totalNumberOfItems = new Long(query.getResultList().size());
            //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
            query.setFirstResult(Math.toIntExact(pageNumber * size));
            query.setMaxResults(Math.toIntExact(size));
            PaginationInfo<Product> paginationInfo = new PaginationInfo<>(size, pageNumber, totalNumberOfItems, query.getResultList());
            return  paginationInfo;
        } else if (typeOfSort.equals("popularity")) {
            return null;
        } else {
            return null;
        }
    }
}
