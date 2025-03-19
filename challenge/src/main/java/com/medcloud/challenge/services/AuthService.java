package com.medcloud.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.medcloud.challenge.dtos.UserDTO;
import com.medcloud.challenge.model.User;
import com.medcloud.challenge.repository.UserRepository;
import com.medcloud.challenge.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(User userPar) {
        User user = userRepository.findByEmail(userPar.getEmail());
        if (user == null) {
            throw new RuntimeException("Invalid email,user not found");
        }

        userPar.setUsername(user.getUsername());

        if (!passwordEncoder.matches(userPar.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String role = user.getRole();

        return jwtUtil.generateToken(user.getEmail(), role);
    }

    public void encoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
