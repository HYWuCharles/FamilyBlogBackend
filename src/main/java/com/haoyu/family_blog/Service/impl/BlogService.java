package com.haoyu.family_blog.Service.impl;

import com.github.pagehelper.PageHelper;
import com.haoyu.family_blog.Model.Blog;
import com.haoyu.family_blog.Model.BlogExample;
import com.haoyu.family_blog.Service.IBlogService;
import com.haoyu.family_blog.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Haoyu WU on 2020/4/14.
 */
@Service
public class BlogService implements IBlogService {

    @Resource
    BlogMapper blogMapper;
    @Resource
    RedisService redisService;
    @Value("${redis.cache.draft.firstlevel.prefix}")
    private String redisDraftPrefix;
    @Value("${draft.expiration}")
    private int draftExpiration;

    @Override
    public List<Blog> findAllBlogs() {
        return blogMapper.selectByExampleWithBLOBs(new BlogExample());
    }

    @Override
    public List<Blog> findAllBlogsByUser(Long id) {
        BlogExample blogExample = new BlogExample();
        blogExample.createCriteria().andCreatedByEqualTo(id);
        return blogMapper.selectByExampleWithBLOBs(blogExample);
    }

    @Override
    public List<Blog> findBlogsByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return blogMapper.selectByExampleWithBLOBs(new BlogExample());
    }

    @Override
    public List<Blog> findBlogsByUserAndByPage(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        BlogExample blogExample = new BlogExample();
        blogExample.createCriteria().andCreatedByEqualTo(userId);
        return blogMapper.selectByExampleWithBLOBs(blogExample);
    }

    @Override
    public int createBlog(Blog blog) {
        return blogMapper.insertSelective(blog);
    }

    @Override
    public int updateBlog(Long id, Blog blog) {
        blog.setId(id);
        return blogMapper.updateByPrimaryKeySelective(blog);
    }

    @Override
    public int deleteBlog(Long id) {
        return blogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Blog findBlogById(Long id) {
        return blogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int changeLikeBlogById(Long id, Blog blog) {
        blog.setId(id);
        return blogMapper.updateByPrimaryKeySelective(blog);
    }

    @Override
    public String saveDraft(String userName, String uuid, Blog blog) {
        HashMap<String, Object> blogHashMap = createBlogHashmap(blog);
        if (uuid == null) {
            uuid = UUID.randomUUID().toString().replace("-", "");
            // 设置一级缓存，即userId <-> Set<UUID>，该缓存记录该用户所有的草稿的二级缓存的key
            redisService.setSet(userName, uuid);
        }
        // 设置实际二级，即prefix:username:uuid <-> BlogHashMap<String, Object>
        redisService.setAllHash(redisDraftPrefix + userName + ":" + uuid, blogHashMap);
        // 刷新草稿过期时间
        redisService.expire(redisDraftPrefix + userName + ":" + uuid, draftExpiration);
        return uuid;
    }

    @Override
    public void deleteDraft(String userName, String key) {
        // 删除二级缓存
        redisService.deleteHash(redisDraftPrefix + userName + ":" + key);
        // 从一级缓存中删除
        redisService.deleteSetElement(userName, key);
        // 如果一级缓存为空
        if (redisService.isEmptySet(userName)) {
            redisService.remove(userName);
        }
    }

    @Override
    public Blog getDraft(String userName, String key) {
        HashMap<String, Object> blogHashmap = redisService.getAllHash(redisDraftPrefix + userName + ":" + key);
        return createBlogFromHashmap(blogHashmap);
    }

    @Override
    public List<Blog> getAllDrafts(String userName) {
        List<Blog> blogs = new ArrayList<>();
        Set<String> uuids = redisService.getSet(userName);
        for (String uuid : uuids) {
            Blog blog = getDraft(userName, uuid);
            blogs.add(blog);
        }
        return blogs;
    }

    @Override
    public List<Blog> getMostRecentFive() {
        List<Blog> blogs = findAllBlogs();
        if (blogs.size() > 5) {
            blogs = blogs.subList(blogs.size() - 5, blogs.size());
        }
        return blogs;
    }

    private HashMap<String, Object> createBlogHashmap(Blog blog) {
        HashMap<String, Object> blogHashMap = new HashMap<>();
        try {
            Field[] fields = Blog.class.getDeclaredFields();
            for (Field field : fields) {
                if ("serialVersionUID".equals(field.getName())) continue;
                field.setAccessible(true);
                Object o = field.get(blog);
                String fieldName = field.getName();
                blogHashMap.put(fieldName, o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blogHashMap;
    }

    private Blog createBlogFromHashmap(HashMap<String, Object> hashMap) {
        Blog blog = new Blog();
        try {
            Class clazz = blog.getClass();
            for (String key : hashMap.keySet()) {
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                field.set(blog, hashMap.get(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blog;
    }

    private List<Blog> reverse(List<Blog> arr) {
        List<Blog> blogs = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            blogs.add(arr.get(arr.size() - i - 1));
        }
        return blogs;
    }
}
