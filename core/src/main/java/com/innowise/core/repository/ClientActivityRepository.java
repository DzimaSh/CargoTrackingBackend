package com.innowise.core.repository;

import com.innowise.core.entity.client.ClientActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientActivityRepository extends JpaRepository<ClientActivity, Integer> {
}
