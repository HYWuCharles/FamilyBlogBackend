package com.haoyu.family_blog.Service;

/**
 * Created by Haoyu WU on 2020/4/16.
 */
public interface IAuthService {

    String generateVerificationCode(String uuid);

    boolean verifyVerificationCode(String uuid, String code);

    void deleteVerificationCode(String uuid);
}
