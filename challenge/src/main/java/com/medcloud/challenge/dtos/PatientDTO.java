package com.medcloud.challenge.dtos;

import java.util.Date;

import com.medcloud.challenge.model.Address;

public record PatientDTO(
                Long id,
                String firstName,
                String lastName,
                String phoneNumber,
                String email,
                Date birthDay,
                Address address) {

}
