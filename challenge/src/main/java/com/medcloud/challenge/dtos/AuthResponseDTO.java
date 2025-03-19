package com.medcloud.challenge.dtos;

public record AuthResponseDTO(
        String token, String role, PartialUserDTO userDto

) {

}
