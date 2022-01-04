package com.kesen.tech.messagegateway.aop;

import com.kesen.tech.messagegateway.anno.DistriLimitAnno;
import com.kesen.tech.messagegateway.redis.DistributedLimit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @className: com.kesen.tech.messagegateway.aop-> LimitAspect
 * @description:
 * @author: kesen
 * @createDate: 2022-01-04 17:29
 * @version: 1.0
 */
@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LimitAspect {
    @Autowired
    DistributedLimit distributedLimit;

    @Pointcut("@annotation(com.kesen.tech.messagegateway.anno.DistriLimitAnno)")
    public void limit() {
    }


    @Before("limit()")
    public void beforeLimit(JoinPoint joinPoint) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistriLimitAnno distriLimitAnno = method.getAnnotation(DistriLimitAnno.class);
        String key = distriLimitAnno.limitKey();
        int limit = distriLimitAnno.limit();
        Boolean exceededLimit = distributedLimit.distributedLimit(key, String.valueOf(limit));
        if (!exceededLimit) {
            throw new RuntimeException("exceeded limit");
        }
    }
}
