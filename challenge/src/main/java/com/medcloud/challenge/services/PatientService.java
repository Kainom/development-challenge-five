package com.medcloud.challenge.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.medcloud.challenge.dtos.PatientDTO;
import com.medcloud.challenge.exceptions.err.AddressInvalidException;
import com.medcloud.challenge.exceptions.err.FieldInvalidException;
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
    private AddressService addressService;
    private Adapter adapter;

    public PatientService(PatientRepository patientRepository,
            AddressRepository addressRepository, Adapter adapter, AddressService addressService) {
        this.patientRepository = patientRepository;
        this.addressRepository = addressRepository;
        this.adapter = adapter;
        this.addressService = addressService;

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

    @SuppressWarnings("Is not possible null,because the previous if")
    public PatientDTO storePatient(PatientDTO patient) {
        // first store the address
        Patient newPatient = patientRepository.findByEmail(patient.email());

        if (!addressService.checkFieldOfAddress(patient.address().zipCode(), patient.address())) {
            throw new AddressInvalidException("Invalid address");
        }

        if (patient.address().zipCode().length() != 8)
            throw new FieldInvalidException("Invalid zip code");

        if (newPatient != null)
            throw new FieldInvalidException("Email already exists");

        Address address = addressRepository.save(adapter.addressDtoToModel(patient.address()));
        newPatient = adapter.patientDtoToModel(patient);
        newPatient.setAddress(address); // set address in patient

        newPatient = patientRepository.save(newPatient);

        return adapter.patientModelToDto(newPatient);
    }

    public PatientDTO updatePatientById(Long id, PatientDTO patientDTO) {
        Patient patient;
        if (patientDTO.email() != null) {
            patient = patientRepository.findByEmail(patientDTO.email());
            if (patient != null && !patient.getId().equals(id))
                throw new FieldInvalidException("Email already exists");
        }

        patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException());
        Address address = patient.getAddress();

        // verifica se os dados não são nulos,evitando erros.
        // caso forem nulos,utiliza os dados anteriores do mesmo model
        if (patientDTO.firstName() != null)
            patient.setFirstName(patientDTO.firstName());
        if (patientDTO.lastName() != null)
            patient.setLastName(patientDTO.lastName());
        if (patientDTO.phoneNumber() != null)
            patient.setPhoneNumber(patientDTO.phoneNumber());
        if (patientDTO.email() != null)
            patient.setEmail(patientDTO.email());
        if (patientDTO.birthDay() != null)
            patient.setBirthDay(patientDTO.birthDay());

        if (patientDTO.address() != null) {
            Address newAddress = addressService.updateAddress(patientDTO.address(), address);
            patient.setAddress(newAddress); // update address in patient
        }

        patient = patientRepository.save(patient);
        return adapter.patientModelToDto(patient);
    }

    public void deletePatientById(Long id) {
        patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException());
        patientRepository.deleteById(id);
    }

}
