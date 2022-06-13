package com.innowise.core.repository;

import com.innowise.core.entity.client.Client;
import com.innowise.core.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>, JpaSpecificationExecutor<Client> {
    Page<Client> findAll(Specification specification, Pageable pageable);
    Client getClientByAdminInfo(User user);
}
