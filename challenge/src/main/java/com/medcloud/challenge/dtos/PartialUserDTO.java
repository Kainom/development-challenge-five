package com.medcloud.challenge.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PartialUserDTO(
        @NotBlank(message = "Username  is mandatory") String username,
        @Email(message = "Invalid e-mail") String email) 
 {}
