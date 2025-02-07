package com.example.FinalProject.GlobalExceptionHandling;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(GeneralException.class)
    ResponseEntity<ExceptionBody> handleRuntimException(GeneralException generalException)
    {
        ExceptionBody eb = new ExceptionBody();
        eb.setMessage(generalException.getLocalizedMessage());
        eb.setStatusCode(HttpStatus.NOT_FOUND);
        return ResponseEntity.status(eb.getStatusCode()).body(eb);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ExceptionBodyForMethodArgumentException> handleMethodArgumentException(MethodArgumentNotValidException notValid)
    {
        ExceptionBodyForMethodArgumentException body = new ExceptionBodyForMethodArgumentException();

        List<FieldError> fieldErrors = notValid.getFieldErrors();
        List<String> getFieldErrors = new ArrayList<>();
        for (FieldError fe: fieldErrors)
        {
            getFieldErrors.add(fe.getField() + ":" + fe.getDefaultMessage());
        }

        body.setStatusCode(notValid.getStatusCode());
        body.setErrorFieldList(getFieldErrors);
        return ResponseEntity.status(body.getStatusCode()).body(body);
    }

}
