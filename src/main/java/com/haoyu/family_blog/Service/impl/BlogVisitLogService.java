package com.haoyu.family_blog.Service.impl;

import com.github.pagehelper.PageHelper;
import com.haoyu.family_blog.Model.BlogVisitLog;
import com.haoyu.family_blog.Model.BlogVisitLogExample;
import com.haoyu.family_blog.Service.IBlogVisitLogService;
import com.haoyu.family_blog.mapper.BlogVisitLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Haoyu WU on 2020/4/24.
 */
@Service
public class BlogVisitLogService implements IBlogVisitLogService {

    @Resource
    BlogVisitLogMapper blogVisitLogMapper;

    @Override
    public void insertNewLog(Long userId, Long blogId) {
        BlogVisitLog blogVisitLog = new BlogVisitLog();
        blogVisitLog.setBlogid(blogId);
        blogVisitLog.setUserid(userId);
        blogVisitLog.setVisittime(new Date());
        blogVisitLog.setHide(0);
        blogVisitLogMapper.insertSelective(blogVisitLog);
    }

    @Override
    public void deleteSingleLogByUserIdAndBlogId(Long userId, Long blogId) {
        BlogVisitLogExample example = new BlogVisitLogExample();
        example.createCriteria().andUseridEqualTo(userId).andBlogidEqualTo(blogId);
        blogVisitLogMapper.deleteByExample(example);
    }

    @Override
    public void hideSingleBlogVisitById(Long id) {
        BlogVisitLog blogVisitLog = blogVisitLogMapper.selectByPrimaryKey(id);
        blogVisitLog.setHide(1);
        blogVisitLogMapper.updateByPrimaryKey(blogVisitLog);
    }

    @Override
    public List<BlogVisitLog> findAllHistory(Long userId) {
        BlogVisitLogExample blogVisitLogExample = new BlogVisitLogExample();
        blogVisitLogExample.createCriteria().andUseridEqualTo(userId);
        return blogVisitLogMapper.selectByExample(blogVisitLogExample);
    }

    @Override
    public List<BlogVisitLog> findHistoryByPage(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        BlogVisitLogExample blogVisitLogExample = new BlogVisitLogExample();
        blogVisitLogExample.createCriteria().andUseridEqualTo(userId);
        return blogVisitLogMapper.selectByExample(blogVisitLogExample);
    }
}
