package com.haoyu.family_blog.Service;

import com.haoyu.family_blog.Model.BlogVisitLog;

import java.util.List;

/**
 * Created by Haoyu WU on 2020/4/24.
 */
public interface IBlogVisitLogService {
    void insertNewLog(Long userId, Long blogId);

    void deleteSingleLogByUserIdAndBlogId(Long userId, Long blogId);

    void hideSingleBlogVisitById(Long id);

    List<BlogVisitLog> findAllHistory(Long userId);

    List<BlogVisitLog> findHistoryByPage(Long userId, int pageNum, int pageSize);
}
