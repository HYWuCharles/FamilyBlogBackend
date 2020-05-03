package com.haoyu.family_blog.Controller;

import com.haoyu.family_blog.Common.CommonResult;
import com.haoyu.family_blog.Model.User;
import com.haoyu.family_blog.Service.impl.LoginService;
import com.haoyu.family_blog.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Haoyu WU on 2020/4/15.
 */
@RestController
public class StatusController {
    @Resource
    private LoginService loginService;
    @Autowired
    private JwtUtils jwtUtils;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @GetMapping(path = "/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @PostMapping(path = "/login")
    public CommonResult login(@RequestBody User userInRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            // TODO: Jump to index.html
            return CommonResult.forbidden("Already logged in");
        }
         */
        String token = loginService.userLogIn(userInRequest.getUsername(), userInRequest.getPassword(), request.getRemoteAddr(),
                request.getRemoteAddr(), request.getHeader("User-Agent"));
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    /*
    没什么用，前端不发Authorization的Header就行了
     */
    @GetMapping(path = "/logout")
    public CommonResult logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(tokenHeader, null);
        return CommonResult.success("Successfully logged out");
    }
}
