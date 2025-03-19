package com.medcloud.challenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medcloud.challenge.dtos.PatientDTO;
import com.medcloud.challenge.model.Patient;
import com.medcloud.challenge.services.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patients") // Endpoint: http://localhost:8080/api/v1/patients
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/")
    public ResponseEntity<List<PatientDTO>> getPatientDTOs() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{email}")
    public ResponseEntity<PatientDTO> getPatientByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(patientService.getPatientByEmail(email));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PostMapping("/")
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patient) {
        return new ResponseEntity<>(patientService.storePatient(patient), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@Valid @PathVariable("id") Long id,
            @RequestBody PatientDTO patientDTO) {
        return ResponseEntity.ok(patientService.updatePatientById(id, patientDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatientById(id);
        return ResponseEntity.noContent().build();

    }

}
