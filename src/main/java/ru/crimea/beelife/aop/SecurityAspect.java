package ru.crimea.beelife.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.crimea.beelife.model.User;
import ru.crimea.beelife.service.BaseService;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class SecurityAspect {

    @Pointcut("@annotation(DataAccess)")
    public void dataAccessPointcut() {
    }

    @Before("dataAccessPointcut()")
    public void userPermissionCheck(JoinPoint joinPoint) throws Throwable {
        BaseService object = (BaseService) joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Class<?> clazz = methodSignature.getDeclaringType();
        Method method = clazz.getDeclaredMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        DataAccess annotation = method.getAnnotation(DataAccess.class);
        for (Parameter parameter : method.getParameters()) {
            if (annotation.value().equals(parameter.getName())) {
                User user;
                Long id = (Long) args[0];
                if (annotation.isParent()) {
                    user = object.getUserFromParentObjectId(id);
                } else {
                    user = object.getUserFromObjectId(id);
                }
                object.checkUserPermission(user);
            }
        }
    }
}
