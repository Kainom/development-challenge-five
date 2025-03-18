package com.medcloud.challenge.dtos;

import java.util.Date;

import com.medcloud.challenge.model.Address;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PatientDTO(
        Long id,
        @NotBlank(message = "First name is mandatory") String firstName,
        @NotBlank(message = "Last name is mandatory") String lastName,
        @NotBlank(message = "Phone number is mandatory") String phoneNumber,
        @Email(message = "Invalid e-mail") @NotBlank(message = "E-mail is mandatory") String email,
        Date birthDay,
        AddressDTO address) {

}
