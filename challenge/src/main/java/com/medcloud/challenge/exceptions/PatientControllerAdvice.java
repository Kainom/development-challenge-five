package com.medcloud.challenge.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.medcloud.challenge.dtos.ErrorDTO;
import com.medcloud.challenge.exceptions.err.AddressInvalidException;
import com.medcloud.challenge.exceptions.err.CepInvalidException;
import com.medcloud.challenge.exceptions.err.ErroLoginException;
import com.medcloud.challenge.exceptions.err.FieldInvalidException;
import com.medcloud.challenge.exceptions.err.PatientNotFoundException;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class PatientControllerAdvice {
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlePatientNotFound(PatientNotFoundException patientNotFoundException) {
        String message = "Patient not Found";
        Integer status = HttpStatus.NOT_FOUND.value();

        ErrorDTO errorDTO = new ErrorDTO(status, message, new Date());
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> handleRuntimeException(RuntimeException ex) {
        String message = "Bad Request";
        Integer status = HttpStatus.BAD_REQUEST.value();

        ErrorDTO errorDTO = new ErrorDTO(status, message, new Date());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ CepInvalidException.class, AddressInvalidException.class })
    public ResponseEntity<ErrorDTO> handlCepException(RuntimeException ex) {
        String message = ex.getMessage();
        Integer status = HttpStatus.BAD_REQUEST.value();

        ErrorDTO errorDTO = new ErrorDTO(status, message, new Date());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(FieldInvalidException.class)
    public ResponseEntity<ErrorDTO> handlFieldException(RuntimeException ex) {
        String message = ex.getMessage();
        Integer status = HttpStatus.BAD_REQUEST.value();

        ErrorDTO errorDTO = new ErrorDTO(status, message, new Date());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ErroLoginException.class)
    public ResponseEntity<ErrorDTO> handleLoginException(RuntimeException ex) {
        String message = ex.getMessage();
        Integer status = HttpStatus.BAD_REQUEST.value();

        ErrorDTO errorDTO = new ErrorDTO(status, message, new Date());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);

    }

    // errors cathc of valid,this is a default form of catch the messages
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }

}
