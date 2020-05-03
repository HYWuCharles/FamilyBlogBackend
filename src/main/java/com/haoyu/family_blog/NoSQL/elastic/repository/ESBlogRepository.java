package com.haoyu.family_blog.NoSQL.elastic.repository;

import com.haoyu.family_blog.NoSQL.elastic.document.ESBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by Haoyu WU on 2020/4/24.
 */
public interface ESBlogRepository extends ElasticsearchRepository<ESBlog, Long> {

    Page<ESBlog> findESBlogByTitle(String title, Pageable pageable);
}
