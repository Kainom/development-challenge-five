package com.medcloud.challenge.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotation.Adapt;
import org.springframework.stereotype.Service;

import com.medcloud.challenge.dtos.AddressDTO;
import com.medcloud.challenge.dtos.PatientDTO;
import com.medcloud.challenge.exceptions.err.PatientNotFoundException;
import com.medcloud.challenge.model.Address;
import com.medcloud.challenge.model.Patient;
import com.medcloud.challenge.patterns.adapters.Adapter;
import com.medcloud.challenge.repository.AddressRepository;
import com.medcloud.challenge.repository.PatientRepository;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    private AddressRepository addressRepository;
    private Adapter adapter;

    public PatientService(PatientRepository patientRepository,
            AddressRepository addressRepository, Adapter adapter) {
        this.patientRepository = patientRepository;
        this.addressRepository = addressRepository;
        this.adapter = adapter;

    }

    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();

        if (patients.isEmpty())
            return null;

        return patients.stream()
               .map(adapter::patientModelToDto)
               .collect(Collectors.toList());
    }

    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException());
        return adapter.patientModelToDto(patient);
    }

    public PatientDTO getPatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email);
        if (patient == null) {
            throw new PatientNotFoundException();
        }

        return adapter.patientModelToDto(patient);
    }

    public PatientDTO storePatient(Patient patient) {
        // first store the address
        Address address = addressRepository.save(patient.getAddress());
        patient.setAddress(address); // set addres that was persisted

        patient = patientRepository.save(patient);

        return adapter.patientModelToDto(patient);
    }

    public PatientDTO updatePatientById(Long id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException());

        if (patient.getFirstName() != null)
            patient.setFirstName(patientDTO.firstName());
        if (patient.getLastName() != null)
            patient.setLastName(patientDTO.lastName());
        if (patient.getPhoneNumber() != null)
            patient.setPhoneNumber(patientDTO.phoneNumber());
        if (patient.getEmail() != null)
            patient.setEmail(patientDTO.email());
        if (patient.getBirthDay() != null)
            patient.setBirthDay(patientDTO.birthDay());
        if (patientDTO.address() != null) 
            patient.setAddress(adapter.addressDtoToModel(patientDTO.address()));


        patient = patientRepository.save(patient);
        return adapter.patientModelToDto(patient);
    }

    public void deletePatientById(Long id) {
        patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException());
        patientRepository.deleteById(id);
    }

}
