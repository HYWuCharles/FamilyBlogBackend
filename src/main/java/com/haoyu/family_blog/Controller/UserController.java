package com.haoyu.family_blog.Controller;

import com.haoyu.family_blog.Common.CommonResult;
import com.haoyu.family_blog.Model.User;
import com.haoyu.family_blog.Service.impl.*;
import com.haoyu.family_blog.Utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Haoyu WU on 2020/4/15.
 */
@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private LoginService loginService;
    @Autowired
    BlogVisitLogService blogVisitLogService;
    @Autowired
    JwtUtils jwtUtils;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping(path = "/listAll")
    public CommonResult<List<User>> findAllUsers() {
        return CommonResult.success(userService.findAllUsers());
    }

    @GetMapping(path = "/{id}")
    public CommonResult<User> findUserById(@PathVariable("id") Long id) {
        return CommonResult.success(userService.findUserById(id));
    }

    @GetMapping(path = "/getAuthCode")
    public CommonResult getCode(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String code = authService.generateVerificationCode(uuid);
        request.getSession().setAttribute("AuthCodeUUID", uuid);
        return CommonResult.success(code);
    }

    @PostMapping(path = "/uploadAvatar")
    public CommonResult uploadAvatar(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request) {
        if (avatar == null) return CommonResult.failed("未选择头像！");
        if (avatar.getOriginalFilename() != null) {
            String fileName = avatar.getOriginalFilename();
            System.out.println("-----###*****" + fileName);
            if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".bmp")) {
                return CommonResult.forbidden("文件格式错误");
            }
        }
        User user = findUserByToken(request);
        String path = imageService.uploadAvatar(avatar, user);
        if (path == null) return CommonResult.failed("上传失败！");
        LOGGER.debug("uploadAvatar success:{}", path);
        return CommonResult.success("上传成功");
    }

    @GetMapping(path = "/current")
    public CommonResult<User> getIndexAvatar(HttpServletRequest request) {
        User user = findUserByToken(request);
        return CommonResult.success(user);
    }

    @PostMapping(path = "/registry")
    public CommonResult createUser(@RequestBody User user, @RequestParam("verifyCode") String verifyCode,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Validate data
        if ("".equals(user.getUsername()) || user.getUsername() == null) {
            return CommonResult.failed("用户名不能为空！");
        }
        // TODO: 密码格式检测
        if ("".equals(user.getPassword()) || user.getPassword() == null) {
            return CommonResult.failed("密码不能为空！");
        }
        if ("".equals(verifyCode) || verifyCode == null) {
            return CommonResult.failed("验证码不能为空！");
        }
        if (userService.findUserByUserName(user.getUsername()).size() != 0) {
            return CommonResult.failed("用户名已存在！");
        }
        String uuid = (String) request.getSession().getAttribute("AuthCodeUUID");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String unCodedPassword = user.getPassword();
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        if (userService.createUser(user, verifyCode, uuid) == 1) {
            LOGGER.debug("createUser success:{}", user);
            // request.getSession().setAttribute("user", user);
            // response.sendRedirect("/login.html");
            String token = loginService.userLogIn(user.getUsername(), unCodedPassword, request.getRemoteAddr(),
                    request.getRemoteAddr(), request.getHeader("User-Agent"));
            if (token == null) {
                return CommonResult.validateFailed("用户名或密码错误");
            }
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            return CommonResult.success(tokenMap);
        } else {
            LOGGER.debug("createUser failed:{}", user);
            return CommonResult.failed("操作失败");
        }
    }

    @GetMapping(path = "/delete/{id}")
    public CommonResult deleteUser(@PathVariable("id") Long id) {
        if (userService.deleteUser(id) == 1) {
            LOGGER.debug("deleteUser success:{}", id);
            return CommonResult.success(id);
        } else {
            LOGGER.debug("deleteUser failed:{}", id);
            return CommonResult.failed("操作失败");
        }
    }

    @PostMapping(path = "/update/{id}")
    public CommonResult updateUser(@PathVariable("id") Long id,
                                   @RequestBody User user) {
        if (userService.updateUser(id, user) == 1) {
            LOGGER.debug("updateUser success:{}", id);
            return CommonResult.success(id);
        } else {
            LOGGER.debug("updateUser failed:{}", id);
            return CommonResult.failed("操作失败");
        }
    }

    @GetMapping(path = "/history/{userId}")
    public CommonResult findVisitHistory(@PathVariable("userId") Long userId, @RequestParam("pageNum") int pageNum,
                                         @RequestParam("pageSize") int pageSize) {
        return CommonResult.success(blogVisitLogService.findHistoryByPage(userId, pageNum, pageSize));
    }

    @GetMapping(path = "/history/delete/{blogVisitId}")
    public CommonResult hideVisitHistory(@PathVariable("blogVisitId") Long blogVisitId) {
        blogVisitLogService.hideSingleBlogVisitById(blogVisitId);
        return CommonResult.success("操作成功");
    }

    private User findUserByToken(HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromToken(request.getHeader(tokenHeader).substring(this.tokenHead.length()));
        return userService.findUserByUserName(username).get(0);
    }
}
