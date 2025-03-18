package com.medcloud.challenge.dtos;

public record AddressDTO(
    Long id,
    String street,
    String city,
    String state,
    String neighborhood,
    String zipCode,
    Integer number
) {
    
}
