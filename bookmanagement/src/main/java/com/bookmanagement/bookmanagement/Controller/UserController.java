package com.bookmanagement.bookmanagement.Controller;

import com.bookmanagement.bookmanagement.Security.AuthRequest;
import com.bookmanagement.bookmanagement.Security.JwtService;
import com.bookmanagement.bookmanagement.Service.UserService;
import com.bookmanagement.bookmanagement.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;


    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtService jwtService,  UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/auth/new")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return userService.addUser(userInfo);
    }

    @PostMapping("/auth/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
