package com.bookmanagement.bookmanagement.Service;

import com.bookmanagement.bookmanagement.Entity.UserInfo;
import com.bookmanagement.bookmanagement.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository repository;

    @Autowired
    public UserService(UserInfoRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "Entity added to system.";
    }
}