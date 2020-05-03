package com.haoyu.family_blog.Exception;

/**
 * Created by Haoyu WU on 2020/4/24.
 */
public class JwtAuthenticationFailureException extends RuntimeException {
    /**
     * 错误信息
     */
    protected String errorMsg;

    public JwtAuthenticationFailureException() {
        super();
    }

    public JwtAuthenticationFailureException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
