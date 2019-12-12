package com.ajla.auction.repo;

import com.ajla.auction.model.Product;
import com.ajla.auction.model.Bid;
import com.ajla.auction.model.ProductInfoBid;
import com.ajla.auction.model.Category;
import com.ajla.auction.model.PriceNumberProducts;
import com.ajla.auction.model.PriceProductInfo;
import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.Characteristic;
import com.ajla.auction.model.NumberOfProductsInfo;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;

import java.time.LocalDate;
import java.util.Objects;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final EntityManager em;
    private final CategoryRepository categoryRepository;
    private final BidRepository bidRepository;

    @Autowired
    public ProductRepositoryImpl(final EntityManager em,
                                 final CategoryRepository categoryRepository,
                                 final BidRepository bidRepository) {
        Objects.requireNonNull(em, "em must not be null.");
        Objects.requireNonNull(categoryRepository, "categoryRepository must not be null.");
        Objects.requireNonNull( bidRepository, " bidRepository must not be null.");
        this.categoryRepository = categoryRepository;
        this.em = em;
        this.bidRepository = bidRepository;
    }

    @Override
    public List<Product> getAllFeatureProducts() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        final Root<Product> product = cq.from(Product.class);

        cq.where(cb.equal(product.get("feature"), true));

        final TypedQuery<Product> query = em.createQuery(cq);
        query.setMaxResults(4);
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

        return  query.getResultList();
    }
    @Override
    public PaginationInfo<Product> getAllLastChanceProducts(final Long pageNumber, final Long size) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        final Root<Product> product = cq.from(Product.class);

        final CriteriaQuery<Long> cqForProductsSize = cb.createQuery(Long.class);
        final Root<Product> productForProductsSize = cqForProductsSize.from(Product.class);

        cqForProductsSize.select(cb.count(productForProductsSize))
                .where(cb.or(
                        cb.equal(productForProductsSize.get("endDate"), LocalDate.now()),
                        cb.equal(productForProductsSize.get("endDate"), LocalDate.now().plusDays(1))
                ));

        cq.where(cb.or(
                cb.equal(product.get("endDate"), LocalDate.now()),
                cb.equal(product.get("endDate"), LocalDate.now().plusDays(1))
        ))
                .orderBy(cb.asc(product.get("endDate")));
        final TypedQuery<Product> queryForListProducts = em.createQuery(cq);
        //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
        queryForListProducts.setFirstResult(Math.toIntExact(pageNumber * size));
        queryForListProducts.setMaxResults(Math.toIntExact(size));
        final List<Product> listOfProducts = queryForListProducts.getResultList();
        //set total number of last chance products
        final TypedQuery<Long> queryForSizeOfListProducts = em.createQuery(cqForProductsSize);
        final Long totalNumberOfItems = queryForSizeOfListProducts.getSingleResult();

        return new PaginationInfo<>(size, pageNumber, totalNumberOfItems, listOfProducts);
    }
    @Override
    public PaginationInfo<Product> getAllNewArrivalProducts(final Long pageNumber, final Long size) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        final Root<Product> product = cq.from(Product.class);

        final CriteriaQuery<Long> cqForSizeOfProducts = cb.createQuery(Long.class);
        final Root<Product> productForSizeOfProducts = cqForSizeOfProducts.from(Product.class);

        cqForSizeOfProducts.select(cb.count(productForSizeOfProducts))
                .where(cb.and(
                        cb.greaterThanOrEqualTo(productForSizeOfProducts.get("endDate"), LocalDate.now()),
                        cb.lessThanOrEqualTo(productForSizeOfProducts.get("startDate"), LocalDate.now())
                ));
        cq.where(cb.and(
                cb.greaterThanOrEqualTo(product.get("endDate"), LocalDate.now()),
                cb.lessThanOrEqualTo(product.get("startDate"), LocalDate.now())
        ))
                .orderBy( cb.desc(product.get("startDate")));

        final TypedQuery<Product> queryForListOfProducts = em.createQuery(cq);
        //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
        queryForListOfProducts.setFirstResult(Math.toIntExact(pageNumber * size));
        queryForListOfProducts.setMaxResults(Math.toIntExact(size));
        final List<Product> listOfProducts = queryForListOfProducts.getResultList();

        //set total number of last chance products
        final TypedQuery<Long> queryForSizeListOfProducts = em.createQuery(cqForSizeOfProducts);
        final Long totalNumberOfItems = queryForSizeListOfProducts.getSingleResult();

        return new PaginationInfo<>(size, pageNumber, totalNumberOfItems, listOfProducts);
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
        return !query.getResultList().isEmpty();
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
                NumberOfProductsInfo categoryInfo = new NumberOfProductsInfo(category.getId(), category.getName(), Collections.emptyList(), null);
                List<NumberOfProductsInfo> listOfSubcategoryInfo = new ArrayList<>();
                long numberOfProductsByCategory = 0;
                for (Category subcategory : category.getSubcategories()) {
                    cq.where(cb.and(
                            cb.equal(product.get("subcategory"), subcategory.getId()),
                            cb.lessThanOrEqualTo(product.get("startDate"), LocalDate.now()),
                            cb.greaterThanOrEqualTo(product.get("endDate"), LocalDate.now())
                    ));
                    query = em.createQuery(cq);
                    NumberOfProductsInfo subcategoryInfo = new NumberOfProductsInfo(subcategory.getId(), subcategory.getName(), Collections.emptyList(), (long) query.getResultList().size());
                    listOfSubcategoryInfo.add(subcategoryInfo);
                    numberOfProductsByCategory += query.getResultList().size();
                }
                categoryInfo.setChildren(listOfSubcategoryInfo);
                categoryInfo.setNumberOfProducts(numberOfProductsByCategory);
                allCategories.add(categoryInfo);
            }
        }
        return allCategories;
    }

    @Override
    public NumberOfProductsInfo numberOfProductsByCharacteristic(final Characteristic characteristic,
                                                                 final Long subcategoryId,
                                                                 final List<Long> listOfCharacteristicClicked,
                                                                 final String searchUser,
                                                                 final Double lowerBound,
                                                                 final Double upperBound) {
        final Category subcategory = categoryRepository.findCategoryById(subcategoryId);
        TypedQuery<Long> query;
        NumberOfProductsInfo mainCharacteristic = new NumberOfProductsInfo(characteristic.getId(), characteristic.getName(), Collections.emptyList(), null);
        List<NumberOfProductsInfo> allCategoriesOfMainCharacteristic = new ArrayList<>();
        Long numberOfProductsBelongToMainCharacteristic = (long) 0;
        for (Characteristic oneOfCharacteristic: characteristic.getAllCharacteristic()) {
            String sqlQuery = "SELECT COUNT(p) FROM Characteristic c JOIN c.products p" +
                    " WHERE c.id =:characteristics_id" +
                    " AND p.startDate <=:dateNow AND p.endDate >=:dateNow";
            if (subcategoryId != null) {
                sqlQuery += " AND p.subcategory =:subcategory";
            }
            if (listOfCharacteristicClicked != null) {
                sqlQuery += " AND p.id IN (SELECT p.id FROM Product p JOIN p.characteristics c WHERE" +
                        " c.id IN (:listOfCharacteristics) GROUP BY p.id HAVING COUNT(c.id)=:listLength)";
            }
            if (searchUser != null) {
                sqlQuery += " AND lower(p.title) LIKE lower(:searchFromUser)";
            }
            if (lowerBound != null && upperBound !=null) {
                sqlQuery += " AND p.startPrice >=:lowerBound AND p.startPrice <=:upperBound";
            }
            query = em.createQuery(sqlQuery, Long.class);
            query.setParameter("characteristics_id", oneOfCharacteristic.getId());
            if (subcategoryId != null) {
                query.setParameter("subcategory", subcategory);
            }
            if (listOfCharacteristicClicked != null) {
                query.setParameter("listOfCharacteristics", listOfCharacteristicClicked);
                query.setParameter("listLength", (long) listOfCharacteristicClicked.size());
            }
            if (searchUser != null) {
                String searchFromUser = "%" + searchUser + "%";
                query.setParameter("searchFromUser", searchFromUser);
            }
            if (lowerBound != null && upperBound !=null) {
                query.setParameter("lowerBound", lowerBound);
                query.setParameter("upperBound", upperBound);
            }
            query.setParameter("dateNow", LocalDate.now());
            NumberOfProductsInfo categoryOfCharacteristic = new NumberOfProductsInfo(oneOfCharacteristic.getId(),
                    oneOfCharacteristic.getName(), Collections.emptyList(), query.getSingleResult());
            allCategoriesOfMainCharacteristic.add(categoryOfCharacteristic);
            numberOfProductsBelongToMainCharacteristic += query.getSingleResult();
        }
        mainCharacteristic.setChildren(allCategoriesOfMainCharacteristic);
        mainCharacteristic.setNumberOfProducts(numberOfProductsBelongToMainCharacteristic);
        return mainCharacteristic;
    }
    @Override
    public PriceProductInfo numberOfProductsByPrice(final Long subcategoryId,
                                                    final List<Long> listOfCharacteristicsClicked,
                                                    final String searchUser) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PriceNumberProducts> cq = cb.createQuery(PriceNumberProducts.class);
        final Root<Product> product = cq.from(Product.class);

        CriteriaQuery<Double> cqForAvgPrice = cb.createQuery(Double.class);
        final Root<Product> productForAvgPrice = cqForAvgPrice.from(Product.class);

        if (subcategoryId == null && listOfCharacteristicsClicked == null) {
            if (searchUser == null) {
                cq.multiselect(product.get("startPrice"), cb.count(product))
                .where(cb.and(
                        cb.greaterThanOrEqualTo(product.get("endDate"), LocalDate.now()),
                        cb.lessThanOrEqualTo(product.get("startDate"), LocalDate.now())
                ));
                cq.groupBy(product.get("startPrice")).orderBy(cb.asc(product.get("startPrice")));

                cqForAvgPrice.select(cb.avg(productForAvgPrice.get("startPrice")))
                        .where(cb.and(
                                cb.greaterThanOrEqualTo(productForAvgPrice.get("endDate"), LocalDate.now()),
                                cb.lessThanOrEqualTo(productForAvgPrice.get("startDate"), LocalDate.now())
                        ));
            } else {
                String searchValue = "%" + searchUser.toLowerCase() + "%";
                cq.multiselect(product.get("startPrice"), cb.count(product))
                .where(cb.and(
                        cb.greaterThanOrEqualTo(product.get("endDate"), LocalDate.now()),
                        cb.lessThanOrEqualTo(product.get("startDate"), LocalDate.now()),
                        cb.like(cb.lower(product.get("title")), searchValue)
                ));
                cq.groupBy(product.get("startPrice")).orderBy(cb.asc(product.get("startPrice")));

                cqForAvgPrice.select(cb.avg(productForAvgPrice.get("startPrice")))
                        .where(cb.and(
                                cb.greaterThanOrEqualTo(productForAvgPrice.get("endDate"), LocalDate.now()),
                                cb.lessThanOrEqualTo(productForAvgPrice.get("startDate"), LocalDate.now()),
                                cb.like(cb.lower(productForAvgPrice.get("title")), searchValue)
                        ));
            }

            PriceProductInfo priceProductInfo = new PriceProductInfo();
            priceProductInfo.setAvgPrice(em.createQuery(cqForAvgPrice).getSingleResult());
            priceProductInfo.setPriceNumber(em.createQuery(cq).getResultList());

            return priceProductInfo;

        } else if (subcategoryId != null && listOfCharacteristicsClicked == null) {
            Category subcategory = categoryRepository.findCategoryById(subcategoryId);

            if (subcategory != null) {
                if (searchUser == null) {
                    cq.multiselect(product.get("startPrice"), cb.count(product).alias("value"))
                            .where(cb.and(
                                    cb.equal(product.get("subcategory"), subcategory),
                                    cb.greaterThanOrEqualTo(product.get("endDate"), LocalDate.now()),
                                    cb.lessThanOrEqualTo(product.get("startDate"), LocalDate.now())
                            ));
                    cq.groupBy(product.get("startPrice")).orderBy(cb.asc(product.get("startPrice")));

                    cqForAvgPrice.select(cb.avg(productForAvgPrice.get("startPrice")))
                    .where(cb.and(
                            cb.equal(productForAvgPrice.get("subcategory"), subcategory),
                            cb.greaterThanOrEqualTo(productForAvgPrice.get("endDate"), LocalDate.now()),
                            cb.lessThanOrEqualTo(productForAvgPrice.get("startDate"), LocalDate.now())
                    ));
                } else {
                    String searchValue = "%" + searchUser.toLowerCase() + "%";
                    cq.multiselect(product.get("startPrice"), cb.count(product).alias("value"))
                            .where(cb.and(
                                    cb.equal(product.get("subcategory"), subcategory),
                                    cb.greaterThanOrEqualTo(product.get("endDate"), LocalDate.now()),
                                    cb.lessThanOrEqualTo(product.get("startDate"), LocalDate.now()),
                                    cb.like(cb.lower(product.get("title")), searchValue)
                            ));
                    cq.groupBy(product.get("startPrice")).orderBy(cb.asc(product.get("startPrice")));

                    cqForAvgPrice.select(cb.avg(productForAvgPrice.get("startPrice")))
                            .where(cb.and(
                                    cb.equal(productForAvgPrice.get("subcategory"), subcategory),
                                    cb.greaterThanOrEqualTo(productForAvgPrice.get("endDate"), LocalDate.now()),
                                    cb.lessThanOrEqualTo(productForAvgPrice.get("startDate"), LocalDate.now()),
                                    cb.like(cb.lower(productForAvgPrice.get("title")), searchValue)
                            ));
                }

                PriceProductInfo priceProductInfo = new PriceProductInfo();
                priceProductInfo.setAvgPrice(em.createQuery(cqForAvgPrice).getSingleResult());
                priceProductInfo.setPriceNumber(em.createQuery(cq).getResultList());

                return priceProductInfo;
            }
            PriceProductInfo priceProductInfo = new PriceProductInfo();
            priceProductInfo.setAvgPrice(null);
            priceProductInfo.setPriceNumber(Collections.emptyList());
            return priceProductInfo;

        } else if (subcategoryId == null) {
            CriteriaQuery<Product> cqForProductId = cb.createQuery(Product.class);
            final Root<Product> productForProductId = cqForProductId.from(Product.class);
            Join<Product, Characteristic> characteristicForProductId = productForProductId.join("characteristics");

            cqForProductId.select(productForProductId.get("id"))
                    .where(cb.in(characteristicForProductId.get("id")).value(listOfCharacteristicsClicked));
            cqForProductId.groupBy(productForProductId.get("id"))
                    .having(cb.equal(cb.count(characteristicForProductId.get("id")), listOfCharacteristicsClicked.size()));

            final TypedQuery<Product> listOfProductsIdWithCharacteristics = em.createQuery(cqForProductId);
            final List<Product> productsIdWithCharacteristics = listOfProductsIdWithCharacteristics.getResultList();

            if (productsIdWithCharacteristics.size() != 0) {
                if (searchUser == null) {
                    cq.multiselect(product.get("startPrice"), cb.count(product).alias("value"))
                            .where(cb.and(
                                    cb.in(product.get("id")).value(productsIdWithCharacteristics),
                                    cb.greaterThanOrEqualTo(product.get("endDate"), LocalDate.now()),
                                    cb.lessThanOrEqualTo(product.get("startDate"), LocalDate.now())
                            ));
                    cq.groupBy(product.get("startPrice")).orderBy(cb.asc(product.get("startPrice")));

                    cqForAvgPrice.select(cb.avg(productForAvgPrice.get("startPrice")))
                            .where(cb.and(
                                    cb.in(productForAvgPrice.get("id")).value(productsIdWithCharacteristics),
                                    cb.greaterThanOrEqualTo(productForAvgPrice.get("endDate"), LocalDate.now()),
                                    cb.lessThanOrEqualTo(productForAvgPrice.get("startDate"), LocalDate.now())
                            ));
                } else {
                    String searchValue = "%" + searchUser.toLowerCase() + "%";
                    cq.multiselect(product.get("startPrice"), cb.count(product).alias("value"))
                            .where(cb.and(
                                    cb.in(product.get("id")).value(productsIdWithCharacteristics),
                                    cb.greaterThanOrEqualTo(product.get("endDate"), LocalDate.now()),
                                    cb.lessThanOrEqualTo(product.get("startDate"), LocalDate.now()),
                                    cb.like(cb.lower(product.get("title")), searchValue)
                            ));
                    cq.groupBy(product.get("startPrice")).orderBy(cb.asc(product.get("startPrice")));

                    cqForAvgPrice.select(cb.avg(productForAvgPrice.get("startPrice")))
                            .where(cb.and(
                                    cb.in(productForAvgPrice.get("id")).value(productsIdWithCharacteristics),
                                    cb.greaterThanOrEqualTo(productForAvgPrice.get("endDate"), LocalDate.now()),
                                    cb.lessThanOrEqualTo(productForAvgPrice.get("startDate"), LocalDate.now()),
                                    cb.like(cb.lower(productForAvgPrice.get("title")), searchValue)
                            ));
                }

                PriceProductInfo priceProductInfo = new PriceProductInfo();
                priceProductInfo.setAvgPrice(em.createQuery(cqForAvgPrice).getSingleResult());
                priceProductInfo.setPriceNumber(em.createQuery(cq).getResultList());

                return priceProductInfo;
            }
            PriceProductInfo priceProductInfo = new PriceProductInfo();
            priceProductInfo.setAvgPrice(null);
            priceProductInfo.setPriceNumber(Collections.emptyList());
            return priceProductInfo;

        } else {
            Category subcategory = categoryRepository.findCategoryById(subcategoryId);

            CriteriaQuery<Long> cqForProductId = cb.createQuery(Long.class);
            final Root<Product> productForProductId = cqForProductId.from(Product.class);
            Join<Product, Characteristic> characteristicForProductId = productForProductId.join("characteristics");

            cqForProductId.select(productForProductId.get("id"))
                    .where(cb.in(characteristicForProductId.get("id")).value(listOfCharacteristicsClicked));
            cqForProductId.groupBy(productForProductId.get("id"))
                    .having(cb.equal(cb.count(characteristicForProductId.get("id")), listOfCharacteristicsClicked.size()));

            final TypedQuery<Long> listOfProductsIdWithCharacteristics = em.createQuery(cqForProductId);
            final List<Long> productsIdWithCharacteristics = listOfProductsIdWithCharacteristics.getResultList();

            if (productsIdWithCharacteristics.size() != 0) {
                if (searchUser == null) {
                cq.multiselect(product.get("startPrice"), cb.count(product).alias("value"))
                        .where(cb.and(
                                cb.equal(product.get("subcategory"), subcategory),
                                cb.in(product.get("id")).value(productsIdWithCharacteristics),
                                cb.greaterThanOrEqualTo(product.get("endDate"), LocalDate.now()),
                                cb.lessThanOrEqualTo(product.get("startDate"), LocalDate.now())
                                ));
                cq.groupBy(product.get("startPrice")).orderBy(cb.asc(product.get("startPrice")));

                cqForAvgPrice.select(cb.avg(productForAvgPrice.get("startPrice")))
                        .where(cb.and(
                                cb.equal(productForAvgPrice.get("subcategory"), subcategory),
                                cb.in(productForAvgPrice.get("id")).value(productsIdWithCharacteristics),
                                cb.greaterThanOrEqualTo(productForAvgPrice.get("endDate"), LocalDate.now()),
                                cb.lessThanOrEqualTo(productForAvgPrice.get("startDate"), LocalDate.now())
                        ));
            } else {
                String searchValue = "%" + searchUser.toLowerCase() + "%";
                cq.multiselect(product.get("startPrice"), cb.count(product).alias("value"))
                        .where(cb.and(
                                cb.equal(product.get("subcategory"), subcategory),
                                cb.in(product.get("id")).value(productsIdWithCharacteristics),
                                cb.greaterThanOrEqualTo(product.get("endDate"), LocalDate.now()),
                                cb.lessThanOrEqualTo(product.get("startDate"), LocalDate.now()),
                                cb.like(cb.lower(product.get("title")), searchValue)
                        ));
                cq.groupBy(product.get("startPrice")).orderBy(cb.asc(product.get("startPrice")));

                cqForAvgPrice.select(cb.avg(productForAvgPrice.get("startPrice")))
                        .where(cb.and(
                                cb.equal(productForAvgPrice.get("subcategory"), subcategory),
                                cb.in(productForAvgPrice.get("id")).value(productsIdWithCharacteristics),
                                cb.greaterThanOrEqualTo(productForAvgPrice.get("endDate"), LocalDate.now()),
                                cb.lessThanOrEqualTo(productForAvgPrice.get("startDate"), LocalDate.now()),
                                cb.like(cb.lower(productForAvgPrice.get("title")), searchValue)
                        ));
            }

                PriceProductInfo priceProductInfo = new PriceProductInfo();
                priceProductInfo.setAvgPrice(em.createQuery(cqForAvgPrice).getSingleResult());
                priceProductInfo.setPriceNumber(em.createQuery(cq).getResultList());

                return priceProductInfo;
            }
            PriceProductInfo priceProductInfo = new PriceProductInfo();
            priceProductInfo.setAvgPrice(null);
            priceProductInfo.setPriceNumber(Collections.emptyList());
            return priceProductInfo;
        }
    }
    @Override
    public PaginationInfo<Product> getAllProductsBySort(final String typeOfSort,
                                                        final Long subcategoryId,
                                                        final Long filterColorId,
                                                        final Long filterSizeId,
                                                        final Double lowerBound,
                                                        final Double upperBound,
                                                        final String searchUser,
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
        if (lowerBound != null && upperBound != null && (subcategoryId != null || (filterColorId != null || filterSizeId !=null))) {
            sqlQuery += " AND p.startPrice >=:lowerBound AND p.startPrice <=:upperBound";
        } else if (lowerBound != null && upperBound != null) {
            sqlQuery += " WHERE p.startPrice >=:lowerBound AND p.startPrice <=:upperBound";
        }
        if (searchUser != null && ((lowerBound != null && upperBound != null) || (subcategoryId != null || (filterColorId != null || filterSizeId !=null)))) {
            sqlQuery += " AND lower(p.title) LIKE lower(:searchFromUser)";
        } else if (searchUser != null) {
            sqlQuery += " WHERE lower(p.title) LIKE lower(:searchFromUser)";
        }
        boolean parametersAreNull = (subcategoryId == null && filterSizeId == null && filterColorId == null && lowerBound == null && upperBound == null && searchUser == null);

        if (typeOfSort.equals("default")) {
            if (parametersAreNull) {
                sqlQuery += " WHERE p.endDate >=:dateNow AND p.startDate <=:dateNow";
            } else {
                sqlQuery += " AND p.endDate >=:dateNow AND p.startDate <=:dateNow";
            }
                query = em.createQuery(sqlQuery, Product.class);
                if (filterColorId != null && filterSizeId !=null) {
                    query.setParameter("color_id", filterColorId);
                    query.setParameter("size_id", filterSizeId);
                } else if (filterColorId != null) {
                    query.setParameter("filter_id", filterColorId);
                } else if (filterSizeId !=null) {
                    query.setParameter("filter_id", filterSizeId);
                }
                if (subcategory != null) {
                    query.setParameter("subcategory", subcategory);
                }
                if (lowerBound != null && upperBound != null) {
                    query.setParameter("lowerBound", lowerBound);
                    query.setParameter("upperBound", upperBound);
                }
                if (searchUser != null) {
                    String searchFromUser = "%" + searchUser + "%";
                    query.setParameter("searchFromUser", searchFromUser);
                }
                query.setParameter("dateNow", LocalDate.now());
                if (query.getResultList().isEmpty()) {
                    return null;
                }
                //set total number of last chance products
                final Long totalNumberOfItems = (long) query.getResultList().size();
                //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
                query.setFirstResult(Math.toIntExact(pageNumber * size));
                query.setMaxResults(Math.toIntExact(size));
            return new PaginationInfo<>(size, pageNumber, totalNumberOfItems, query.getResultList());
        } else if (typeOfSort.equals("newness")) {
            if (subcategory == null && filterSizeId == null && filterColorId == null && lowerBound == null && upperBound == null && searchUser == null) {
                sqlQuery += " WHERE p.endDate >=:dateNow AND p.startDate <=:dateNow ORDER BY p.startDate DESC";
            } else {
                sqlQuery += " AND p.endDate >=:dateNow AND p.startDate <=:dateNow ORDER BY p.startDate DESC";
            }
            query = em.createQuery(sqlQuery, Product.class);
            if (filterColorId != null && filterSizeId !=null) {
                query.setParameter("color_id", filterColorId);
                query.setParameter("size_id", filterSizeId);
            } else if (filterColorId != null) {
            query.setParameter("filter_id", filterColorId);
            } else if (filterSizeId !=null) {
            query.setParameter("filter_id", filterSizeId);
            }
            if (subcategory != null) {
                query.setParameter("subcategory", subcategory);
            }
            if (lowerBound != null && upperBound != null) {
                query.setParameter("lowerBound", lowerBound);
                query.setParameter("upperBound", upperBound);
            }
            if (searchUser != null) {
                String searchFromUser = "%" + searchUser + "%";
                query.setParameter("searchFromUser", searchFromUser);
            }
            query.setParameter("dateNow", LocalDate.now());
            if (query.getResultList().isEmpty()) {
                return null;
            }
            //set total number of last chance products
            final Long totalNumberOfItems = (long) query.getResultList().size();
            //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
            query.setFirstResult(Math.toIntExact(pageNumber * size));
            query.setMaxResults(Math.toIntExact(size));
            return new PaginationInfo<>(size, pageNumber, totalNumberOfItems, query.getResultList());
        } else if (typeOfSort.equals("priceLowest")) {
            if (parametersAreNull) {
                sqlQuery += " WHERE p.endDate >=:dateNow AND p.startDate <=:dateNow ORDER BY p.startPrice ASC";
            } else {
                sqlQuery += " AND p.endDate >=:dateNow AND p.startDate <=:dateNow ORDER BY p.startPrice ASC";
            }
            query = em.createQuery(sqlQuery, Product.class);
            if (filterColorId != null && filterSizeId !=null) {
                query.setParameter("color_id", filterColorId);
                query.setParameter("size_id", filterSizeId);
            } else if (filterColorId != null) {
                query.setParameter("filter_id", filterColorId);
            } else if (filterSizeId !=null) {
                query.setParameter("filter_id", filterSizeId);
            }
            if (subcategory != null) {
                query.setParameter("subcategory", subcategory);
            }
            if (lowerBound != null && upperBound != null) {
                query.setParameter("lowerBound", lowerBound);
                query.setParameter("upperBound", upperBound);
            }
            if (searchUser != null) {
                String searchFromUser = "%" + searchUser + "%";
                query.setParameter("searchFromUser", searchFromUser);
            }
            query.setParameter("dateNow", LocalDate.now());
            if (query.getResultList().isEmpty()) {
                return null;
            }
            //set total number of last chance products
            final Long totalNumberOfItems = (long) query.getResultList().size();
            //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
            query.setFirstResult(Math.toIntExact(pageNumber * size));
            query.setMaxResults(Math.toIntExact(size));
            return new PaginationInfo<>(size, pageNumber, totalNumberOfItems, query.getResultList());
        } else if (typeOfSort.equals("priceHighest")) {
            if (parametersAreNull) {
                sqlQuery += " WHERE p.endDate >=:dateNow AND p.startDate <=:dateNow ORDER BY p.startPrice DESC";
            } else {
                sqlQuery += " AND p.endDate >=:dateNow AND p.startDate <=:dateNow ORDER BY p.startPrice DESC";
            }
            query = em.createQuery(sqlQuery, Product.class);
            if (filterColorId != null && filterSizeId !=null) {
                query.setParameter("color_id", filterColorId);
                query.setParameter("size_id", filterSizeId);
            } else if (filterColorId != null) {
                query.setParameter("filter_id", filterColorId);
            } else if (filterSizeId !=null) {
                query.setParameter("filter_id", filterSizeId);
            }
            if (subcategory != null) {
                query.setParameter("subcategory", subcategory);
            }
            if (lowerBound != null && upperBound != null) {
                query.setParameter("lowerBound", lowerBound);
                query.setParameter("upperBound", upperBound);
            }
            if (searchUser != null) {
                String searchFromUser = "%" + searchUser + "%";
                query.setParameter("searchFromUser", searchFromUser);
            }
            query.setParameter("dateNow", LocalDate.now());
            if (query.getResultList().isEmpty()) {
                return null;
            }
            //set total number of last chance products
            final Long totalNumberOfItems = (long) query.getResultList().size();
            //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
            query.setFirstResult(Math.toIntExact(pageNumber * size));
            query.setMaxResults(Math.toIntExact(size));
            return new PaginationInfo<>(size, pageNumber, totalNumberOfItems, query.getResultList());
        } else {
            return null;
        }
    }
    @Override
    public PaginationInfo<ProductInfoBid> getAllActiveProductsOfSeller(final Long idSeller, final Long pageNumber, final Long size) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        final Root<Product> product = cq.from(Product.class);

        final CriteriaQuery<Long> cqForProductsSize = cb.createQuery(Long.class);
        final Root<Product> productForProductsSize = cqForProductsSize.from(Product.class);


        cqForProductsSize.select(cb.count(productForProductsSize))
                .where(cb.and(
                        cb.greaterThanOrEqualTo(product.get("endDate"), LocalDate.now()),
                        cb.equal(product.get("seller"), idSeller)
                ));

        cq.where(cb.and(
                cb.greaterThanOrEqualTo(product.get("endDate"), LocalDate.now()),
                cb.equal(product.get("seller"), idSeller)
        ))
                .orderBy(cb.asc(product.get("endDate")));

        final TypedQuery<Product> queryForListProducts = em.createQuery(cq);
        //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
        queryForListProducts.setFirstResult(Math.toIntExact(pageNumber * size));
        queryForListProducts.setMaxResults(Math.toIntExact(size));
        final List<Product> listOfProducts = queryForListProducts.getResultList();
        final List<ProductInfoBid> listOfProductsInfoBid = new ArrayList<>();
        listOfProducts.forEach(p -> {
            final List<Bid> bidsOfProduct = bidRepository.bidsByProduct(p.getId());
            ProductInfoBid pib = new ProductInfoBid();
            if (!bidsOfProduct.isEmpty()) {
                pib.setHighestBid(bidsOfProduct.get(0).getValue());
                pib.setNumberOfBids((long) bidsOfProduct.size());
            } else {
                pib.setHighestBid(null);
                pib.setNumberOfBids(null);
            }
            pib.setEndDate(p.getEndDate());
            pib.setImages(p.getImages());
            pib.setId(p.getId());
            pib.setTitle(p.getTitle());
            pib.setStartPrice(p.getStartPrice());
            listOfProductsInfoBid.add(pib);
        });
        //set total number of last chance products
        final TypedQuery<Long> queryForSizeOfListProducts = em.createQuery(cqForProductsSize);
        final Long totalNumberOfItems = queryForSizeOfListProducts.getSingleResult();

        return new PaginationInfo<>(size, pageNumber, totalNumberOfItems, listOfProductsInfoBid);
    }
    @Override
    public PaginationInfo<ProductInfoBid> getAllSoldProductsOfSeller(final Long idSeller, final Long pageNumber, final Long size) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        final Root<Product> product = cq.from(Product.class);

        final CriteriaQuery<Long> cqForProductsSize = cb.createQuery(Long.class);
        final Root<Product> productForProductsSize = cqForProductsSize.from(Product.class);


        cqForProductsSize.select(cb.count(productForProductsSize))
                .where(cb.and(
                        cb.lessThan(product.get("endDate"), LocalDate.now()),
                        cb.equal(product.get("seller"), idSeller)
                ));

        cq.where(cb.and(
                cb.lessThan(product.get("endDate"), LocalDate.now()),
                cb.equal(product.get("seller"), idSeller)
        ))
                .orderBy(cb.desc(product.get("endDate")));

        final TypedQuery<Product> queryForListProducts = em.createQuery(cq);
        //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
        queryForListProducts.setFirstResult(Math.toIntExact(pageNumber * size));
        queryForListProducts.setMaxResults(Math.toIntExact(size));
        final List<Product> listOfProducts = queryForListProducts.getResultList();
        final List<ProductInfoBid> listOfProductsInfoBid = new ArrayList<>();
        listOfProducts.forEach(p -> {
            final List<Bid> bidsOfProduct = bidRepository.bidsByProduct(p.getId());
            ProductInfoBid pib = new ProductInfoBid();
            if (!bidsOfProduct.isEmpty()) {
                pib.setHighestBid(bidsOfProduct.get(0).getValue());
                pib.setNumberOfBids((long) bidsOfProduct.size());
            } else {
                pib.setHighestBid(null);
                pib.setNumberOfBids(null);
            }
            pib.setEndDate(p.getEndDate());
            pib.setImages(p.getImages());
            pib.setId(p.getId());
            pib.setTitle(p.getTitle());
            pib.setStartPrice(p.getStartPrice());
            listOfProductsInfoBid.add(pib);
        });
        //set total number of last chance products
        final TypedQuery<Long> queryForSizeOfListProducts = em.createQuery(cqForProductsSize);
        final Long totalNumberOfItems = queryForSizeOfListProducts.getSingleResult();

        return new PaginationInfo<>(size, pageNumber, totalNumberOfItems, listOfProductsInfoBid);

    }
}
