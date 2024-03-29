package com.innowise.core.repository;

import com.innowise.core.entity.client.Client;
import com.innowise.core.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Page<User> findAll(Specification specification, Pageable pageable);
    Optional<User> findByLogin(String login);
    User getByClient(Client client);
}
