package com.haoyu.family_blog.Service;

import com.haoyu.family_blog.Model.User;

import java.util.List;

/**
 * Created by Haoyu WU on 2020/4/14.
 */
public interface IUserService {
    List<User> findAllUsers();

    User findUserById(Long id);

    int createUser(User user, String verifyCode, String uuid);

    int deleteUser(Long id);

    int updateUser(Long id, User user);

    List<User> findUserByUserName(String username);
}
