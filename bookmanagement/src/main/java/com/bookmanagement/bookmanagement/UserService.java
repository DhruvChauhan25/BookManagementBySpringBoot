package com.bookmanagement.bookmanagement;
import  com.bookmanagement.bookmanagement.User;
import  com.bookmanagement.bookmanagement.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    Logger logger= LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        logger.debug("Showing all up the users");
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        logger.debug("Finding all te user");
        return userRepository.findById(Math.toIntExact(id));
    }

    public User saveUser(User user) {
        logger.debug("Saving the book");
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        logger.debug("Deleting up the users");
        userRepository.deleteById(Math.toIntExact(id));
    }
}
