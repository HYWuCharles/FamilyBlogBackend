package com.haoyu.family_blog.NoSQL.elastic.dao;

import com.haoyu.family_blog.NoSQL.elastic.document.ESBlog;

import java.util.List;

/**
 * Created by Haoyu WU on 2020/4/24.
 */
public interface IDaoEsBlogs {

    List<ESBlog> findAllElasticBlogs();

    ESBlog findElasticBlogById(Long id);
}
