package com.medcloud.challenge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medcloud.challenge.dtos.AuthRequestDTO;
import com.medcloud.challenge.dtos.AuthResponseDTO;
import com.medcloud.challenge.dtos.PartialUserDTO;
import com.medcloud.challenge.model.User;
import com.medcloud.challenge.security.JwtUtil;
import com.medcloud.challenge.services.AuthService;
import jakarta.validation.Valid;

/**
 * 
 * @see AuthService AuthService object for authentication operations
 * @see JwtUtil JwtUtil object for generating JWT tokens
 *      This class is responsible for managing user authentication.
 * 
 */
@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * This method is responsible for handling user login requests.
     * It receives an AuthRequestDTO object, which contains the user's
     * email and password. If the login is successful, it returns an
     * AuthResponseDTO object containing the JWT token and user details.
     * 
     * @param requestDTO The AuthRequestDTO object containing the user's email and
     *                   password.
     * @return A ResponseEntity containing authentication response information. *
     * @see AuthRequestDTO
     * @see AuthResponseDTO
     * @see PartialUserDTO
     */
    @PostMapping("/")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO requestDTO) {
        User user = new User();
        user.setEmail(requestDTO.email()); // Ajusta email para username
        user.setPassword(requestDTO.password()); // Ajusta password para password

        String token = authService.login(user);
        PartialUserDTO userDtoPartial = new PartialUserDTO(user.getUsername(), user.getEmail());

        String role = jwtUtil.extractRole(token); // Extrai a role do token
        // Thread.sleep(1000);
        return ResponseEntity.ok(new AuthResponseDTO(token, role, userDtoPartial)); // Retorna token e role

    }

}
