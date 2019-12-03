package com.ajla.auction.repo;

import com.ajla.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User findUserById(Long id);
    User findByEmail (String email);
}
