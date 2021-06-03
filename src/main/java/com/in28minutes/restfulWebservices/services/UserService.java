package com.in28minutes.restfulWebservices.services;

import com.in28minutes.restfulWebservices.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findUsers();

    Optional<User> findUserById(Long id);

    void deleteUserById(Long id);

    User saveUser(User user);
}
