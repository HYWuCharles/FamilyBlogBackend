package com.haoyu.family_blog.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Haoyu WU on 2020/4/26.
 */
@Aspect
@Component
public class ServiceAOP {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "execution(* com.haoyu.family_blog.Service.*.*(..))")
    public void services() {
    }

    @Before(value = "services()")
    public void beforeServiceInvoked(JoinPoint joinPoint) {
        LOGGER.info("----- Service Invoked -----");
        LOGGER.info("Invoked method: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOGGER.info("");
    }
}
