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



/**
 * This class is responsible for managing patient-related operations,
 * including retrieving patient information, storing new patients, updating
 * existing patients, and deleting patients.
 *
 * @see AddressService object for managing address-related operations
 * @see Adapter object for converting between Patient and PatientDTO
 *
 */
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

    /**
     * This method retrieves all patients from the database and converts them to
     * PatientDTO using the adapter.
     *
     * @return List of PatientDTO objects with patient information
     */
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();

        if (patients.isEmpty())
            return null;

        return patients.stream()
                .map(adapter::patientModelToDto)
                .collect(Collectors.toList());
    }

    /**
     * This method retrieves a patient by ID from the database and converts it to
     * PatientDTO using the adapter.
     *
     * @param id ID of the patient to be retrieved
     * @return PatientDTO object with patient information
     * @throws PatientNotFoundException if the patient is not found
     */
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

    /**
     * This method stores a new patient in the database. It first checks if the
     * address is valid and then saves the address and patient to the database.
     *
     * @param patient PatientDTO object with patient information
     * @return PatientDTO object with saved patient information
     * @throws AddressInvalidException if the address is not valid
     * @throws FieldInvalidException   if the email already exists or if the zip code
     *                                 is invalid
     */
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


    /**
     * This method updates an existing patient in the database. It first checks if
     * the address is valid and then updates the address and patient in the database.
     *
     * @param id        ID of the patient to be updated
     * @param patientDTO PatientDTO object with new values to update
     * @return PatientDTO object with updated patient information
     * @throws AddressInvalidException if the address is not valid
     * @throws FieldInvalidException   if the email already exists or if the zip code
     *                                 is invalid
     * the method also checks if exist null values in the patientDTO and if so   keeps the old values in the  patient
     */
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
