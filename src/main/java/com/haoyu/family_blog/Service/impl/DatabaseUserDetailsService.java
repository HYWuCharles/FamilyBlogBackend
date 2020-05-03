package com.haoyu.family_blog.Service.impl;

import com.haoyu.family_blog.Model.SecurityUser;
import com.haoyu.family_blog.Model.User;
import com.haoyu.family_blog.Model.UserExample;
import com.haoyu.family_blog.mapper.UserMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Haoyu WU on 2020/4/21.
 */
@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    @Resource
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(s);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) throw new UsernameNotFoundException("Username: " + s + " not found");
        SecurityUser securityUser = new SecurityUser(users.get(0));
        securityUser.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(users.get(0).getRole()));
        // System.out.println("****** User: " + s + " has authorities: " + securityUser.getAuthorities().toString());
        return securityUser;
    }
}
