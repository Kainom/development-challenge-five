package com.medcloud.challenge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medcloud.challenge.dtos.PartialUserDTO;
import com.medcloud.challenge.dtos.UserDTO;
import com.medcloud.challenge.model.User;
import com.medcloud.challenge.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<PartialUserDTO> createUser(@Valid @RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.storeUser(user));

    }

}
