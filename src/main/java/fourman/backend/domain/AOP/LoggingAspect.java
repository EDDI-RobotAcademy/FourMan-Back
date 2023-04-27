package fourman.backend.domain.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* fourman.backend.domain..*.service.*.*(..))")
    private void serviceLayer() {}

    @Around("serviceLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - startTime;
        String methodName = joinPoint.getSignature().toShortString();
        System.out.println("Method: " + methodName + " executed in " + elapsedTime + "ms");
        return result;
    }
}