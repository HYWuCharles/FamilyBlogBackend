package com.haoyu.family_blog.Service;

import com.haoyu.family_blog.NoSQL.elastic.document.ESBlog;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Haoyu WU on 2020/4/24.
 */
public interface IESBlogService {
    int importAll();

    void deleteEsBlog(Long id);

    ESBlog createEsBlog(Long id);

    void delete(List<Long> ids);


    public Page<ESBlog> search(String title, Integer pageNum, Integer pageSize);
}
