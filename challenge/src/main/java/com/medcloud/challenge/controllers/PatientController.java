package com.medcloud.challenge.controllers;

import java.util.List;

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

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/patients") // Endpoint: http://localhost:8080/api/v1/patients
@AllArgsConstructor
public class PatientController {

    @GetMapping("/")
    public ResponseEntity<List<PatientDTO>> getPatientDTOs() {
        return null;
    }

    @GetMapping("/{email}")
    public ResponseEntity<PatientDTO> getPatientByEmail(@PathVariable("email") String email) {
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable("id") Long id, @RequestBody PatientDTO patientDTO) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Long id) {
        return null;
    }


}
