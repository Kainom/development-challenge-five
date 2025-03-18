package com.medcloud.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medcloud.challenge.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    public Patient findByEmail(String email);


}
