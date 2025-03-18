package com.medcloud.challenge.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.medcloud.challenge.dtos.ErrorDTO;
import com.medcloud.challenge.exceptions.err.PatientNotFoundException;

public class PatientControllerAdvice {
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlePatientNotFound(PatientNotFoundException patientNotFoundException) {
        String message = "Patient not Found";
        Integer status = HttpStatus.NOT_FOUND.value();

        ErrorDTO errorDTO = new ErrorDTO(status, message, new Date());
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

}
