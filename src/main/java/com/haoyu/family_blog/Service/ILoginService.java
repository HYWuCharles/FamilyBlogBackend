package com.haoyu.family_blog.Service;

/**
 * Created by Haoyu WU on 2020/4/16.
 */
public interface ILoginService {
    String userLogIn(String userName, String password, String ip, String address, String user_agent);
}
