package seohan.ottshare.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import seohan.ottshare.enums.ErrorCode;
import seohan.ottshare.exception.LockAcquisitionException;
import seohan.ottshare.exception.LockInterruptedException;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {

    private static final String REDISSON_LOCK_PREFIX = "RLOCK_";

    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;


    @Around("@annotation(seohan.ottshare.aop.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String key = REDISSON_LOCK_PREFIX +
                CustomSpringELParser.getDynamicValue(
                        signature.getParameterNames(),
                        joinPoint.getArgs(),
                        distributedLock.key()
                );
        log.info("lock on [method:{}] [key:{}]", method, key);

        RLock rLock = redissonClient.getLock(key);
        String lockName = rLock.getName();
        try {
            boolean available =
                    rLock.tryLock(
                            distributedLock.waitTime(),
                            distributedLock.leaseTime(),
                            distributedLock.timeUnit()
                    );
            if(!available) {
                throw new LockAcquisitionException(ErrorCode.LOCK_NOT_AVAILABLE);
            }

            log.info("get lock success {}" , key);
            return aopForTransaction.proceed(joinPoint);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LockInterruptedException(ErrorCode.LOCK_INTERRUPTED_ERROR);
        } finally {
            try {
                rLock.unlock();
                log.info("unlock complete [Lock:{}] ", lockName);
            } catch (IllegalMonitorStateException e) {
                log.info("Redisson Lock Already Unlocked");
            }
        }
    }
}
