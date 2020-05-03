package com.haoyu.family_blog.Exception.Handlers;

import com.haoyu.family_blog.Common.CommonResult;
import com.haoyu.family_blog.Exception.JwtAuthenticationFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Haoyu WU on 2020/4/24.
 */
/*
该注解@RestControllerAdvice只能用于处理Controller的异常
若非Controller则会报Url的空指针异常
 */
@RestControllerAdvice
public class JwtAuthenticationFailureExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFailureExceptionHandler.class);

    @ExceptionHandler(value = JwtAuthenticationFailureException.class)
    public CommonResult handleJwtAuthenticationFailureException(HttpServletRequest request,
                                                                JwtAuthenticationFailureException e) {
        System.out.println(request);
        logger.error("Jwt格式验证错误，请求来自: {}", request.getRemoteAddr());
        return CommonResult.validateFailed(e.getMessage());
    }

}
