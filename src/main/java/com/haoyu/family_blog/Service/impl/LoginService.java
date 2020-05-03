package com.haoyu.family_blog.Service.impl;

import com.haoyu.family_blog.Model.User;
import com.haoyu.family_blog.Model.UserExample;
import com.haoyu.family_blog.Model.UserLoginLog;
import com.haoyu.family_blog.Service.ILoginService;
import com.haoyu.family_blog.Utils.JwtUtils;
import com.haoyu.family_blog.mapper.UserLoginLogMapper;
import com.haoyu.family_blog.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Haoyu WU on 2020/4/16.
 */
@Service
public class LoginService implements ILoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    @Resource
    private UserLoginLogMapper userLoginLogMapper;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UserService userService;


    @Override
    public String userLogIn(String userName, String password, String ip, String address, String user_agent) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        String token = null;
        try {
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtUtils.generateToken(userDetails);
            updateLog(userName, ip, address, user_agent);
        } catch (AuthenticationException e) {
            LOGGER.warn("User: {} abnormal login", userName);
        }
        return token;
    }

    public void updateLog(String userName, String IP, String address, String userAgent) {
        // 更新登录信息
        UserLoginLog userLoginLog = new UserLoginLog();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(userName);
        List<User> users = userMapper.selectByExample(userExample);
        userLoginLog.setUserId(users.get(0).getId());
        userLoginLog.setTime(new Date());
        userLoginLog.setIp(IP);
        userLoginLog.setAddress(address);
        userLoginLog.setUserAgent(userAgent);
        userLoginLogMapper.insertSelective(userLoginLog);
    }
}
