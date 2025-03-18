package com.medcloud.challenge.patterns.adapters;

import org.springframework.stereotype.Component;

import com.medcloud.challenge.dtos.PatientDTO;
import com.medcloud.challenge.model.Patient;

@Component
public class AdapterPatient implements IAdapterPatient {

    @Override
    public Patient patientDtoToModel(PatientDTO patientDTO) {
        return new Patient(
                patientDTO.id(),
                patientDTO.firstName(),
                patientDTO.lastName(),
                patientDTO.phoneNumber(),
                patientDTO.email(),
                patientDTO.birthDay());

    }

    @Override
    public PatientDTO patientModelToDto(Patient patient) {
        return new PatientDTO(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getBirthDay());
    }

}
