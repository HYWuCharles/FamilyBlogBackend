package com.haoyu.family_blog.NoSQL.elastic.dao.impl;

import com.haoyu.family_blog.Model.Blog;
import com.haoyu.family_blog.Model.BlogExample;
import com.haoyu.family_blog.NoSQL.elastic.dao.IDaoEsBlogs;
import com.haoyu.family_blog.NoSQL.elastic.document.ESBlog;
import com.haoyu.family_blog.mapper.BlogMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haoyu WU on 2020/4/24.
 */
@Repository
public class DaoEsBlogs implements IDaoEsBlogs {
    @Resource
    private BlogMapper mapper;

    @Override
    public List<ESBlog> findAllElasticBlogs() {
        List<Blog> blogs = mapper.selectByExampleWithBLOBs(new BlogExample());
        List<ESBlog> esBlogs = new ArrayList<>();
        for (Blog blog : blogs) {
            ESBlog esBlog = new ESBlog(blog);
            esBlogs.add(esBlog);
        }
        return esBlogs;
    }

    @Override
    public ESBlog findElasticBlogById(Long id) {
        BlogExample blogExample = new BlogExample();
        blogExample.createCriteria().andIdEqualTo(id);
        List<Blog> blogs = mapper.selectByExampleWithBLOBs(blogExample);
        if (blogs.size() > 0) {
            return new ESBlog(blogs.get(0));
        } else {
            return null;
        }
    }
}
