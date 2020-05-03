package com.haoyu.family_blog.Controller;

import com.haoyu.family_blog.Common.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Haoyu WU on 2020/4/23.
 */
@RestController
@RequestMapping(value = "/admin")
public class BackStageController {

    @GetMapping(value = "/modifyUsers")
    public CommonResult modifyUsers() {
        return CommonResult.success("Success");
    }
}
