package com.example.FinalProject.GlobalExceptionHandling;

import jakarta.persistence.GeneratedValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Globalhandler {

    @ExceptionHandler(GeneralException.class)
    ResponseEntity<ExceptionBody> handleRuntimException(GeneralException generalException)
    {
        ExceptionBody eb = new ExceptionBody();
        eb.setMessage(generalException.getLocalizedMessage());
        eb.setStatusCode(HttpStatus.NOT_FOUND);
        return ResponseEntity.status(eb.getStatusCode()).body(eb);
    }

}
