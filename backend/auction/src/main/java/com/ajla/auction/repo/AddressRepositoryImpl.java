package com.ajla.auction.repo;

import com.ajla.auction.model.Address;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.Objects;

@Repository
public class AddressRepositoryImpl implements AddressRepositoryCustom {
    private final EntityManager em;
    private final TransactionTemplate transactionTemplate;

    public AddressRepositoryImpl(final EntityManager em,
                                 final TransactionTemplate transactionTemplate) {
        Objects.requireNonNull(transactionTemplate, "transactionTemplate must not be null.");
        Objects.requireNonNull(em, "em must not be null.");
        this.em = em;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Address updateAddressOfUser(Address addressInfo) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        final CriteriaUpdate<Address> cq = cb.createCriteriaUpdate(Address.class);
        final Root<Address> address = cq.from(Address.class);

        cq.set("state", addressInfo.getState());
        cq.set("street", addressInfo.getStreet());
        cq.set("country", addressInfo.getCountry());
        cq.set("city", addressInfo.getCity());
        cq.set("zipCode", addressInfo.getZipCode());
        cq.where(cb.equal(address.get("id"), addressInfo.getId()));

        transactionTemplate.execute(transactionStatus -> {
            em.createQuery(cq).executeUpdate();
            transactionStatus.flush();
            return null;
        });
        return addressInfo;
    }
}
