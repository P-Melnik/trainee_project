package trainee.GymApp.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TransactionLoggingAspect {

    @Pointcut("within(trainee.GymApp.facade.Facade)")
    public void packagePointCut() {
    }

    @Around("packagePointCut()")
    public Object logTransactionStart(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        TransactionLogger.logTransactionStart();
        log.info("Method '{}'", proceedingJoinPoint.getSignature().getName());
        Object result = proceedingJoinPoint.proceed();
        TransactionLogger.logTransactionEnd();
        return result;
    }

}
