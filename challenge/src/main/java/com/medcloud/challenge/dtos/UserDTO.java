package com.medcloud.challenge.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank(message = "Username  is mandatory") String username,

        @Email(message = "Invalid e-mail") String email,
        @NotBlank(message = "password is mandatory")

        String password) {

}
