package com.medcloud.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.medcloud.challenge.exceptions.err.ErroLoginException;
import com.medcloud.challenge.model.User;
import com.medcloud.challenge.repository.UserRepository;
import com.medcloud.challenge.security.JwtUtil;

/**
 * AuthService This class is responsible for managing user authentication and
 * password
 * encoding.
 * 
 * @see BCryptPasswordEncoder BCryptPasswordEncoder object for password encoding
 * @see JwtUtil JwtUtil object for generating JWT tokens
 *
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * This method is responsible for authenticating a user.
     * 
     * @param userPar User object containing the email,password,username and default
     *                role ADMIN of the user
     * @return JWT token if authentication is successful
     * @throws ErroLoginException if the email or password is invalid
     */
    public String login(User userPar) {
        User user = userRepository.findByEmail(userPar.getEmail());
        if (user == null) {
            throw new ErroLoginException("Invalid email,user not found");
        }

        userPar.setUsername(user.getUsername());

        if (!passwordEncoder.matches(userPar.getPassword(), user.getPassword())) {
            throw new ErroLoginException("Invalid password");
        }

        String role = user.getRole();

        return jwtUtil.generateToken(user.getEmail(), role);
    }


    /**
     * This method is responsible for encoding the password of a user.
     * 
     * @param user User object containing the password to be encoded
     */
    public void encoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
