package com.laoumri.supsharespringbootsocialmediaapp.annotations.aspects;

import com.laoumri.supsharespringbootsocialmediaapp.annotations.ValidateNotSelf;
import com.laoumri.supsharespringbootsocialmediaapp.nodes.User;
import com.laoumri.supsharespringbootsocialmediaapp.enums.code.EGlobal;
import com.laoumri.supsharespringbootsocialmediaapp.exceptions.global.InvalidOperationException;
import com.laoumri.supsharespringbootsocialmediaapp.security.utils.AuthenticationContext;
import com.laoumri.supsharespringbootsocialmediaapp.utils.ZipUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

@Aspect
public class ValidationAspect {
    @Around("@annotation(com.laoumri.supsharespringbootsocialmediaapp.annotations.ValidateNotSelf)")
    public Object checkNotSelf(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        ValidateNotSelf annotation = method.getAnnotation(ValidateNotSelf.class);

        Map<String, Object> args = ZipUtils.argsToMap(signature.getParameterNames(), pjp.getArgs());
        UUID targetId = (UUID) args.get(annotation.targetArg());

        UUID currentUserId = AuthenticationContext.getCurrentUser().getProfile().getId();

        if (targetId.equals(currentUserId)) {
            throw new InvalidOperationException(EGlobal.OPERATION_FORBIDDEN, "Operation not allowed on yourself");
        }

        return pjp.proceed();
    }
}
