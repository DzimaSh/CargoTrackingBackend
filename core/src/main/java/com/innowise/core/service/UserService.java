package com.innowise.core.service;

import com.innowise.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface UserService {
    User getUserById(Integer id);
    Integer postUser(User user);
    Page<User> getAllUsersByFilterParams(String name, String surname, String patronymic,
                                         Date beforeBornDate, Date afterBornDate, String town,
                                         String street, String house, String flat,
                                         String[] roles, Pageable pageable);
    void deleteUsersById(Integer[] ids);
    void updateUser(User updatedUser, Integer id);
}
