package com.haoyu.family_blog.Service.impl;

import com.haoyu.family_blog.Model.User;
import com.haoyu.family_blog.Model.UserExample;
import com.haoyu.family_blog.Service.IUserService;
import com.haoyu.family_blog.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Haoyu WU on 2020/4/15.
 */
@Service
public class UserService implements IUserService {

    @Resource
    UserMapper userMapper;

    @Resource
    AuthService authService;

    @Override
    public List<User> findAllUsers() {
        return userMapper.selectByExample(new UserExample());
    }

    @Override
    public User findUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int createUser(User user, String verifyCode, String uuid) {
        if (authService.verifyVerificationCode(uuid, verifyCode)) {
            // TODO:进行用户资料设置
            user.setCreateTime(new Date());
            authService.deleteVerificationCode(uuid);
            return userMapper.insertSelective(user);
        } else {
            // TODO: 抽取数值，写入properties文件中
            authService.deleteVerificationCode(uuid);
            return 2;
        }
    }

    @Override
    public int deleteUser(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateUser(Long id, User user) {
        user.setId(id);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<User> findUserByUserName(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        return userMapper.selectByExample(userExample);
    }
}
