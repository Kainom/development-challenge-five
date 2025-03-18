package com.medcloud.challenge.dtos;

import java.util.Date;

public record PatientDTO(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        Date birthDay) {

}
