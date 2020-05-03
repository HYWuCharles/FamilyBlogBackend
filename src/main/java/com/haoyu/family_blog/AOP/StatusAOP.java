package com.haoyu.family_blog.AOP;

import com.haoyu.family_blog.Service.impl.UserService;
import com.haoyu.family_blog.Utils.JwtUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Aspect class for logging in
 * Created by Haoyu WU on 2020/4/16.
 */
@Aspect
@Component
public class StatusAOP {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "execution(public * com.haoyu.family_blog.Service.ILoginService.*(..) )")
    public void aopLogin() {
    }

    @Pointcut(value = "execution(public * com.haoyu.family_blog.Controller.*.*(..))")
    public void aopAllControllerMethod() {
    }

    @Before(value = "aopAllControllerMethod()")
    public void beforeControllerInvoked(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        LOGGER.info("----- Before -----");
        LOGGER.info("URL: " + request.getRequestURL().toString());
        LOGGER.info("Protocol: HTTP; Method: " + request.getMethod());
        LOGGER.info("IP Address: " + request.getRemoteAddr());
        LOGGER.info("Invoked method: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOGGER.info("Params: " + request.getQueryString());
        LOGGER.info("----- Before End -----");
        LOGGER.info("");
    }

    @AfterReturning(pointcut = "aopLogin()", returning = "returnValue")
    public void afterAopLogin(JoinPoint joinPoint, Object returnValue) {
        updateLoginLog(joinPoint, returnValue);

    }

    private void updateLoginLog(JoinPoint joinPoint, Object returnValue) {
        LOGGER.info("----- Login -----");
        LOGGER.info("Method: " + joinPoint.getSignature().getName());
        String value = (String) returnValue;
        if (value != null) {
            String username = jwtUtils.getUserNameFromToken(value);
            LOGGER.info("User {} logged in", username);
        }
        LOGGER.info("----- Login Ends -----");
        LOGGER.info("");
    }
}
