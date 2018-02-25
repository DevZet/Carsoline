package com.carsoline.services;

import com.carsoline.repositories.UserRepository;
import com.carsoline.rest.daos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Dominik on 2017-03-23.
 */
@Component
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findOneById(String id) {
        return userRepository.findOneById(id);
    }

    public User findByEmail(String email) { return userRepository.findOneByEmail(email); }

    public User findByNameAndPassword(String name, String password) { return userRepository.findOneByNameAndPassword(name, password); }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(String id) {
        userRepository.delete(id);
    }
}
