package com.example.FinalProject.GlobalExceptionHandling;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class ExceptionBody {

    private String message;
    private HttpStatusCode statusCode;
}
