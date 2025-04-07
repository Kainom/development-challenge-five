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

/**
 * PatientControllerAdvice
 * This class handles exceptions thrown by the PatientController
 * and provides appropriate error responses.
 * handlePatientNotFound Handles PatientNotFoundException and returns a
 * 404 Not Found response with an error message.
 * handleRuntimeException Handles RuntimeException and returns a 400 Bad
 * Request response with an error message.
 * handlCepException Handles CepInvalidException and AddressInvalidException and
 * returns a 400 Bad Request response with an error message.
 * handlFieldException Handles FieldInvalidException and returns a 400 Bad
 * Request response with an error message.
 * handleLoginException Handles ErroLoginException and returns a 400 Bad Request
 * response with an error message.
 * handleValidationExceptions Handles MethodArgumentNotValidException and
 * returns a map of validation errors.
 *
 */
@ControllerAdvice
public class PatientControllerAdvice {

    /**
     * Handles PatientNotFoundException and returns a 404 Not Found response with an
     * error message.
     *
     * @param patientNotFoundException the exception thrown when a patient is not
     *                                 found
     * @return a ResponseEntity containing the error details
     */
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlePatientNotFound(PatientNotFoundException patientNotFoundException) {
        String message = "Patient not Found";
        Integer status = HttpStatus.NOT_FOUND.value();

        ErrorDTO errorDTO = new ErrorDTO(status, message, new Date());
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles RuntimeException and returns a 400 Bad Request response with an error
     * message.
     *
     * @param ex the exception thrown
     * @return a ResponseEntity containing the error details
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> handleRuntimeException(RuntimeException ex) {
        String message = "Bad Request";
        Integer status = HttpStatus.BAD_REQUEST.value();

        ErrorDTO errorDTO = new ErrorDTO(status, message, new Date());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);

    }

    /**
     * Handles CepInvalidException and AddressInvalidException and returns a 400 Bad
     * Request response with an error message.
     *
     * @param ex the exception thrown
     * @return a ResponseEntity containing the error details
     * @see ErrorDTO
     * @see CepInvalidException
     * @see AddressInvalidException
     */
    @ExceptionHandler({ CepInvalidException.class, AddressInvalidException.class })
    public ResponseEntity<ErrorDTO> handlCepException(RuntimeException ex) {
        String message = ex.getMessage();
        Integer status = HttpStatus.BAD_REQUEST.value();

        ErrorDTO errorDTO = new ErrorDTO(status, message, new Date());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);

    }

    /**
     * Handles FieldInvalidException and returns a 400 Bad Request response with an
     * error message.
     *
     * @param ex the exception thrown
     * @return a ResponseEntity containing the error details
     * @see ErrorDTO
     * @see FieldInvalidException
     */
    @ExceptionHandler(FieldInvalidException.class)
    public ResponseEntity<ErrorDTO> handlFieldException(RuntimeException ex) {
        String message = ex.getMessage();
        Integer status = HttpStatus.BAD_REQUEST.value();

        ErrorDTO errorDTO = new ErrorDTO(status, message, new Date());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);

    }

    /**
     * Handles ErroLoginException and returns a 400 Bad Request response with an
     * error
     * message.
     *
     * @param ex the exception thrown
     * @return a ResponseEntity containing the error details
     * @see ErrorDTO
     * @see ErroLoginException
     */
    @ExceptionHandler(ErroLoginException.class)
    public ResponseEntity<ErrorDTO> handleLoginException(RuntimeException ex) {
        String message = ex.getMessage();
        Integer status = HttpStatus.BAD_REQUEST.value();

        ErrorDTO errorDTO = new ErrorDTO(status, message, new Date());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);

    }

    /**
     * Handles MethodArgumentNotValidException and returns a map of validation
     * errors.
     * Catches validation errors in the request body and returns a 400 Bad
     * For example,the email is invalid or the password is empty.
     * 
     * @param ex the exception thrown
     * @return a ResponseEntity containing the validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }

}
