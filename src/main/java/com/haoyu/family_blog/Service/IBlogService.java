package com.haoyu.family_blog.Service;

import com.haoyu.family_blog.Model.Blog;

import java.util.List;

/**
 * Created by Haoyu WU on 2020/4/14.
 */
public interface IBlogService {
    List<Blog> findAllBlogs();

    List<Blog> findAllBlogsByUser(Long UserId);

    List<Blog> findBlogsByPage(int pageNum, int pageSize);

    List<Blog> findBlogsByUserAndByPage(Long userId, int pageNum, int pageSize);

    int createBlog(Blog blog);

    int updateBlog(Long id, Blog blog);

    int deleteBlog(Long id);

    Blog findBlogById(Long id);

    int changeLikeBlogById(Long id, Blog blog);

    String saveDraft(String userName, String uuid, Blog blog);

    void deleteDraft(String userName, String key);

    Blog getDraft(String userName, String key);

    List<Blog> getAllDrafts(String userName);

    List<Blog> getMostRecentFive();
}
