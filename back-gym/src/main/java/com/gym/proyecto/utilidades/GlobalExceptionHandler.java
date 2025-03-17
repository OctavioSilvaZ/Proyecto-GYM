package com.gym.proyecto.utilidades;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de cualquier excepción general
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        // Retorna un mensaje genérico y el mensaje de la excepción
        return new ResponseEntity<>("Ocurrió un error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // otra:
    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepciones no controladas
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleThrowable(Throwable ex) {
        return new ResponseEntity<>("Error no controlado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
