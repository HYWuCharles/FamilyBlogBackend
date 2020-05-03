package com.haoyu.family_blog.Controller;

import com.haoyu.family_blog.Common.CommonResult;
import com.haoyu.family_blog.NoSQL.elastic.document.ESBlog;
import com.haoyu.family_blog.Service.impl.ESBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Haoyu WU on 2020/4/24.
 */
@RestController
@RequestMapping("/es")
public class ESController {
    @Autowired
    ESBlogService esBlogService;

    @GetMapping("/importAll")
    public CommonResult<Integer> importAll() {
        int count = esBlogService.importAll();
        return CommonResult.success(count);
    }

    @GetMapping("/search")
    public CommonResult search(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        Page<ESBlog> esBlogs = esBlogService.search(keyword, pageNum, pageSize);
        return CommonResult.success(esBlogs.getContent());
    }
}
