package ru.crimea.beelife.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ThrowingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ThrowingAspect.class);

    @AfterThrowing(pointcut = "execution(* ru.crimea.beelife.service.*.*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.error("{}() with arguments ({}) - {}", methodName, args, exception.getMessage(), exception);
    }
}
