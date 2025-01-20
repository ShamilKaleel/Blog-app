package org.ruhuna.blogapp.controller;

import org.ruhuna.blogapp.model.User;
import org.ruhuna.blogapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser( @RequestBody User user) {
        User user1 = userService.registerUser(user);
        return new ResponseEntity<>(user1, HttpStatus.OK );
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {

        return userService.verify(user);
    }

}
