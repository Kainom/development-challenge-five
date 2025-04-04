package com.medcloud.challenge.patterns.adapters;

import org.springframework.stereotype.Component;

import com.medcloud.challenge.dtos.AddressDTO;
import com.medcloud.challenge.dtos.PatientDTO;
import com.medcloud.challenge.model.Address;
import com.medcloud.challenge.model.Patient;

/**
 * @class Adapter
 * @description This class is responsible for converting between DTOs and models.
 * @apiNote  patientDtoToModel Converts PatientDTO to Patient model
 * @apiNote  addressDtoToModel Converts AddressDTO to Address model
 * @apiNote  patientModelToDto Converts Patient model to PatientDTO
 * @apiNote  addressModelToDto Converts Address model to AddressDTO
 *
 */
@Component
public class Adapter {


    public Patient patientDtoToModel(PatientDTO patientDTO) {
        Address address = addressDtoToModel(patientDTO.address()); // Convertendo AddressDTO para Address
        return new Patient(
                patientDTO.id(),
                patientDTO.firstName(),
                patientDTO.lastName(),
                patientDTO.phoneNumber(),
                patientDTO.email(),
                patientDTO.birthDay(),
                address
        );
    }

    // Método para converter de AddressDTO para Address
    public Address addressDtoToModel(AddressDTO addressDTO) {
        return new Address(
                addressDTO.id(),
                addressDTO.street(),
                addressDTO.city(),
                addressDTO.state(),
                addressDTO.neighborhood(),
                addressDTO.zipCode(),
                addressDTO.number()
        );
    }

    // Método para converter de Patient para PatientDTO
    public PatientDTO patientModelToDto(Patient patient) {
        AddressDTO addressDTO = addressModelToDto(patient.getAddress());
        return new PatientDTO(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getBirthDay(),
                addressDTO
        );
    }

    // Método para converter de Address para AddressDTO
    public AddressDTO addressModelToDto(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getNeighborhood(),
                address.getZipCode(),
                address.getNumber()
        );
    }
}
