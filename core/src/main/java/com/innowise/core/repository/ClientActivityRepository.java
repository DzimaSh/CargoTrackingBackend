package com.innowise.core.repository;

import com.innowise.core.entity.client.Client;
import com.innowise.core.entity.client.ClientActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientActivityRepository extends JpaRepository<ClientActivity, Integer> {
    @Transactional
    void deleteByClient(Client client);
}
