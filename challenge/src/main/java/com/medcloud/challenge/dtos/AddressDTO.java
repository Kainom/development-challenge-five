package com.medcloud.challenge.dtos;

public record AddressDTO(
    Long id,
    String street,
    String city,
    String state,
    String country,
    String neighborhood,
    String zipCode,
    Integer number
) {
    
}
