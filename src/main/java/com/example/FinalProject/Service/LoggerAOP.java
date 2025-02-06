package com.example.FinalProject.Service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class LoggerAOP {

   @Pointcut("within(com.example.FinalProject.Service..*)")
    public void justReferMethod()
    {

    }

    @Around(value = "justReferMethod()")
    public Object printStartAndEndTimeWhenMethodWillBeInvoked(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println(joinPoint.getSignature().getName());
        System.out.println(new Date(System.currentTimeMillis()));
        return joinPoint.proceed();
        //System.out.println(new Date(System.currentTimeMillis()));

    }


}
