package com.haoyu.family_blog.Common;

/**
 * Created by Haoyu WU on 2020/4/14.
 */
public enum ResultCode {

    SUCCESS(200, "Success"),
    FAILED(500, "Failed"),
    VALIDATE_FAILED(404, "Token validation failed"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Non-permission"),
    ERROR(501, "Internal error");

    private Integer code;
    private String message;

    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
