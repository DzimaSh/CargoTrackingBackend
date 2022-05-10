package com.innowise.core.service.impl;

import com.innowise.core.entity.User;
import com.innowise.core.repository.UserRepository;
import com.innowise.core.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {
    private final UserRepository repository;

    @Override
    public User getUserById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NullPointerException("account not find" + id));
    }
    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }
    @Override
    public void postUser(User user) {
        repository.save(user);
    }

}
