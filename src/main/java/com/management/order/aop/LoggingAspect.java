package com.management.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Around(
            "execution(* com.management.order.service..*(..)) || " +
                    "execution(* com.management.order.controller..*(..))"
    )
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        System.out.println(
                "Method: " + joinPoint.getSignature() +
                        " executed in " + (end - start) + " ms"
        );

        return result;
    }
}