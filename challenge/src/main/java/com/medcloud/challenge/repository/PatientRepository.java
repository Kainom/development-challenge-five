package com.medcloud.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medcloud.challenge.model.Patient;

public interface PatientRepository extends JpaRepository<Patient,Long> {

}
