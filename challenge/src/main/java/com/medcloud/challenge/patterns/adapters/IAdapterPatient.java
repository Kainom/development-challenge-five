package com.medcloud.challenge.patterns.adapters;

import com.medcloud.challenge.dtos.PatientDTO;
import com.medcloud.challenge.model.Patient;

public interface IAdapterPatient {

    public Patient patientDtoToModel(PatientDTO patientDTO);

    public PatientDTO patientModelToDto(Patient patient);

    
}
