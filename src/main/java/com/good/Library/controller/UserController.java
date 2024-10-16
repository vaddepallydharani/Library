package com.good.Library.controller;

import com.good.Library.entity.User;
import com.good.Library.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<User> registration(@Valid @RequestBody User user) {
            User registeredUser = userService.registration(user);
            return ResponseEntity.ok(registeredUser);
    }
}
