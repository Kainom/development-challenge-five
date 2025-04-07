package com.medcloud.challenge.patterns.adapters;

import org.springframework.stereotype.Component;

import com.medcloud.challenge.dtos.AddressDTO;
import com.medcloud.challenge.dtos.PatientDTO;
import com.medcloud.challenge.model.Address;
import com.medcloud.challenge.model.Patient;

/**
 * This class is responsible for converting between DTOs and models.
 * patientDtoToModel Converts PatientDTO to Patient model
 * addressDtoToModel Converts AddressDTO to Address model
 * patientModelToDto Converts Patient model to PatientDTO
 * addressModelToDto Converts Address model to AddressDTO
 *
 */
@Component
public class Adapter {

    /**
     * Converts PatientDTO to Patient model.
     * 
     * @param patientDTO the PatientDTO to convert
     * @return the converted Patient model
     *
     */
    public Patient patientDtoToModel(PatientDTO patientDTO) {
        Address address = addressDtoToModel(patientDTO.address()); // Convertendo AddressDTO para Address
        return new Patient(
                patientDTO.id(),
                patientDTO.firstName(),
                patientDTO.lastName(),
                patientDTO.phoneNumber(),
                patientDTO.email(),
                patientDTO.birthDay(),
                address);
    }

    /**
     * Converts AddressDTO to Address model.
     * 
     * @param addressDTO the AddressDTO to convert
     * @return the converted Address model
     *
     */
    public Address addressDtoToModel(AddressDTO addressDTO) {
        return new Address(
                addressDTO.id(),
                addressDTO.street(),
                addressDTO.city(),
                addressDTO.state(),
                addressDTO.neighborhood(),
                addressDTO.zipCode(),
                addressDTO.number());
    }

    /**
     * Converts Patient model to PatientDTO.
     * 
     * @param patient the Patient model to convert
     * @return the converted PatientDTO
     *
     */
    public PatientDTO patientModelToDto(Patient patient) {
        AddressDTO addressDTO = addressModelToDto(patient.getAddress());
        return new PatientDTO(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getBirthDay(),
                addressDTO);
    }

    /**
     * Converts Address model to AddressDTO.
     * 
     * @param address the Address model to convert
     * @return the converted AddressDTO
     *
     */
    public AddressDTO addressModelToDto(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getNeighborhood(),
                address.getZipCode(),
                address.getNumber());
    }
}
