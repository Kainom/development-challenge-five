package com.medcloud.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medcloud.challenge.dtos.PartialUserDTO;
import com.medcloud.challenge.dtos.UserDTO;
import com.medcloud.challenge.exceptions.err.FieldInvalidException;
import com.medcloud.challenge.model.User;
import com.medcloud.challenge.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService auth;

    public PartialUserDTO storeUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setUsername(userDTO.username());

        auth.encoder(user);
        User userCheck = userRepository.findByEmail(user.getEmail());
        if (userCheck != null) {
            throw new FieldInvalidException("Email already exists");
        }
        userRepository.save(user);
        return new PartialUserDTO(user.getUsername(), user.getEmail());
    }

}
