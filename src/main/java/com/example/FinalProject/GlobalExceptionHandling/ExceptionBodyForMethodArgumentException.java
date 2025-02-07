package com.example.FinalProject.GlobalExceptionHandling;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@Getter
@Setter
public class ExceptionBodyForMethodArgumentException {

    private List<String> errorFieldList;

    private HttpStatusCode statusCode;

}
