package com.example.FinalProject.Service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Import(LoggerAOP.class)
class LoggerAOPTest {

    @InjectMocks
    LoggerAOP logger;

    @Test
    void testLoggingAspect() throws Throwable {


        // Arrange: Mock ProceedingJoinPoint
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        // Mock Signature (fix for NullPointerException)
        Signature signature = mock(Signature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("sampleMethod"); // Return method name

        // Mock method execution
        when(joinPoint.proceed()).thenReturn("Executed");
        logger.justReferMethod();
        // Create an instance of LoggerAOP
        LoggerAOP loggerAOP = new LoggerAOP();

        // Act: Invoke the aspect method manually
        Object result = loggerAOP.printStartAndEndTimeWhenMethodWillBeInvoked(joinPoint);

        // Assert: Verify method execution
        assertEquals("Executed", result); // Ensure method runs correctly
        verify(joinPoint, times(1)).proceed(); // Ensure method proceeds
    }
}