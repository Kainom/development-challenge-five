package com.medcloud.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medcloud.challenge.dtos.PartialUserDTO;
import com.medcloud.challenge.dtos.UserDTO;
import com.medcloud.challenge.exceptions.err.FieldInvalidException;
import com.medcloud.challenge.model.User;
import com.medcloud.challenge.repository.UserRepository;

/**
 *  This class is responsible for managing user-related operations,
 *              including storing user information and validating user data.
 * @see   AuthService object for authentication-related operations
 *
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService auth;

    /**
     * This method is responsible for storing a user in the database.
     * 
     * @param userDTO UserDTO object containing the email, password, and username of
     *                the user
     * @return PartialUserDTO object containing the username and email of the user
     * @throws FieldInvalidException if the email already exists in the database
     */
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
