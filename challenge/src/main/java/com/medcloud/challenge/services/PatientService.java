package com.medcloud.challenge.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medcloud.challenge.dtos.PatientDTO;
import com.medcloud.challenge.exceptions.err.PatientNotFoundException;
import com.medcloud.challenge.model.Patient;
import com.medcloud.challenge.patterns.adapters.IAdapterPatient;
import com.medcloud.challenge.repository.PatientRepository;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    private IAdapterPatient adapterPatient;

    @Autowired
    public PatientService(PatientRepository patientRepository, IAdapterPatient adapterPatient) {
        this.patientRepository = patientRepository;
        this.adapterPatient = adapterPatient;

    }

    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients
                .stream()
                .map(adapterPatient::patientModelToDto)
                .collect(Collectors.toList());
    }

    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException());
        return adapterPatient.patientModelToDto(patient);
    }

    public PatientDTO getPatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email);
        if (patient == null) {
            throw new PatientNotFoundException();
        }
        return adapterPatient.patientModelToDto(patient);
    }

    public PatientDTO storePatient(PatientDTO patientDTO) {
        Patient patient = patientRepository.save(adapterPatient.patientDtoToModel(patientDTO));
        return adapterPatient.patientModelToDto(patient);
    }

    public PatientDTO updatePatientById(Long id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException() );

        if (patient.getFirstName() == null)
            patient.setFirstName(patientDTO.firstName());
        if (patient.getLastName() == null)
            patient.setLastName(patientDTO.lastName());
        if (patient.getPhoneNumber() == null)
            patient.setPhoneNumber(patientDTO.phoneNumber());
        if (patient.getEmail() == null)
            patient.setEmail(patientDTO.email());
        if (patient.getBirthDay() == null)
            patient.setBirthDay(patientDTO.birthDay());
        if (patientDTO.address() == null)
            patient.setAddress(adapterPatient.patientDtoToModel(patientDTO).getAddress());

        patient = patientRepository.save(patient);
        return adapterPatient.patientModelToDto(patient);
    }

    public void deletePatientById(Long id) {
        patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException());
        patientRepository.deleteById(id);
    }

}
