package org.vaadin.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.model.User;
import org.vaadin.example.repository.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User getUserById(String id) {
        return null;
    }

    @Override
    public User updateUser(String id, User userDetails) {
        return null;
    }

    @Override
    public boolean deleteUser(String id) {
        return false;
    }

    @Override
    public long countUser() {
        return 0;
    }
}
