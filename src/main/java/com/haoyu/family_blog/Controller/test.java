package com.haoyu.family_blog.Controller;

import com.haoyu.family_blog.Model.Blog;
import com.haoyu.family_blog.Service.impl.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Haoyu WU on 2020/4/16.
 */
@RestController
@RequestMapping("/test")
public class test {
    @Autowired
    ImageService imageService;

    @PostMapping("/test1")
    public void test1(@RequestPart("User") String user,
                      @RequestPart("File") String file) {
        System.out.println(user.toString());
        System.out.println(file.toString());
    }

    @GetMapping("/test2")
    public void test2() {
        System.out.println("USER role accessed");
    }

    @PostMapping(path = "/uploadDraft")
    public void uploadDraft(@RequestBody Blog blog, @RequestParam(value = "uuid", required = false) String uuid) {
        System.out.println(uuid);
    }
}
