package com.innowise.core.repository;

import com.innowise.core.entity.User;
import com.innowise.core.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> getAllByBornDateAfter(Date bornDate);
    List<User> getAllByUserRoles(UserRole roles);
    List<User> getAllByBornDateBefore(Date bornDate);
}
