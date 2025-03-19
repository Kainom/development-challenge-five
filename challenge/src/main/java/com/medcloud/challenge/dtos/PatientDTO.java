package com.medcloud.challenge.dtos;

import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PatientDTO(
                Long id,
                @NotBlank(message = "First name is mandatory") String firstName,
                @NotBlank(message = "Last name is mandatory") String lastName,
                @Size(min = 10, max = 13, message = "O telefone deve ter entre 12 e 13 dígitos")

                String phoneNumber,
                @Email(message = "Invalid e-mail") @NotBlank(message = "E-mail is mandatory") String email,
                Date birthDay,
                AddressDTO address) {

}
