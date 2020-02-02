package com.ajla.auction.repo;

import com.ajla.auction.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, AddressRepositoryCustom {
    Address findAddressById(Long id);

}
