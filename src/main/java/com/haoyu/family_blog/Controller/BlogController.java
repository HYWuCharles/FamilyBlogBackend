package com.haoyu.family_blog.Controller;

import com.haoyu.family_blog.Common.CommonResult;
import com.haoyu.family_blog.Model.Blog;
import com.haoyu.family_blog.Model.User;
import com.haoyu.family_blog.Service.impl.*;
import com.haoyu.family_blog.Utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static com.haoyu.family_blog.Common.CommonResult.success;

/**
 * Created by Haoyu WU on 2020/4/14.
 */
@RestController
@RequestMapping(path = "/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogVisitLogService blogVisitLogService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private ESBlogService esBlogService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);

    @GetMapping(path = "/listAll")
    public CommonResult<List<Blog>> findAllBlogs() {
        return success(blogService.findAllBlogs());
    }

    @GetMapping(path = "/listAll/{id}")
    public CommonResult<List<Blog>> findAllBlogsByUser(@PathVariable("id") Long id) {
        return success(blogService.findAllBlogsByUser(id));
    }

    @GetMapping(path = "/list")
    public CommonResult<List<Blog>> findBlogsByPage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "3") int pageSize) {
        return success(blogService.findBlogsByPage(pageNum, pageSize));
    }

    @GetMapping(path = "/list/{id}")
    public CommonResult<List<Blog>> findAllBlogsByUserAndByPage(@PathVariable("id") Long id,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                                @RequestParam(value = "pageSize", defaultValue = "3") int pageSize) {
        return success(blogService.findBlogsByUserAndByPage(id, pageNum, pageSize));
    }

    @PostMapping(path = "/publish")
    public CommonResult createBlog(@RequestBody Blog blog, HttpServletRequest request,
                                   @RequestParam("draftId") String draftId) {
        User user = findUserByToken(request);
        blog.setCreatedBy(user.getId());
        blog.setTime(new Date());
        if (blogService.createBlog(blog) == 1) {
            LOGGER.debug("createBlog success:{}", blog);
            esBlogService.createEsBlog(blog.getId());
            if (draftId != null) {
                // 说明是通过草稿来上传的
                blogService.deleteDraft(user.getUsername(), draftId);
            }
            return CommonResult.success(blog);
        } else {
            LOGGER.debug("createBlog failed:{}", blog);
            return CommonResult.failed("操作失败！");
        }
    }

    @PostMapping(path = "/uploadImages/{blogId}")
    public CommonResult<List<String>> uploadImages(@RequestBody MultipartFile[] files, @PathVariable("blogId") String blogId,
                                                   HttpServletRequest request) {
        List<String> imagePaths = imageService.uploadImages(files, blogId,
                findUserByToken(request).getId());
        if (imagePaths.size() != 0) return CommonResult.success(imagePaths);
        else return CommonResult.failed("upload failed");
    }

    @GetMapping(path = "/images/{blogId}")
    public CommonResult<List<String>> getBlogImages(@PathVariable("blogId") Long blogId) {
        List<String> links = imageService.getImages(blogId);
        // System.out.println("________------###" + links.get(0));
        return CommonResult.success(links);
    }

    @PostMapping(path = "/update/{id}")
    public CommonResult updateBlog(@PathVariable("id") Long id, @RequestBody Blog blog) {
        if (blogService.updateBlog(id, blog) == 1) {
            LOGGER.debug("updateBlog success:{}", blog);
            return success(blog);
        } else {
            LOGGER.debug("updateBlog failed:{}", blog);
            return CommonResult.failed("操作失败！");
        }
    }

    @GetMapping(path = "/delete/{id}")
    public CommonResult deleteBlog(@PathVariable("id") Long id) {
        if (blogService.deleteBlog(id) == 1) {
            LOGGER.debug("deleteBlog success, id:{}", id);
            esBlogService.deleteEsBlog(id);
            return CommonResult.success("操作成功");
        } else {
            LOGGER.debug("deleteBlog failed");
            return CommonResult.failed("操作失败！");
        }
    }

    @GetMapping(path = "/{id}")
    public CommonResult<Blog> findBlogById(@PathVariable("id") Long id, HttpServletRequest request) {
        Blog blog = blogService.findBlogById(id);
        User user = findUserByToken(request);
        blogVisitLogService.insertNewLog(user.getId(), id);
        return success(blog);
    }

    @GetMapping(path = "/like/{id}")
    public CommonResult likeBlogById(@PathVariable("id") Long id) {
        // TODO: 解决问题：可以刷赞
        Blog blog = blogService.findBlogById(id);
        blog.setHearts(blog.getHearts() + 1);
        return success(blogService.changeLikeBlogById(id, blog));
    }

    @GetMapping(path = "/unlike/{id}")
    public CommonResult unlikeBlogById(@PathVariable("id") Long id) {
        // TODO: 解决问题：可以刷赞
        Blog blog = blogService.findBlogById(id);
        blog.setHearts(blog.getHearts() - 1);
        return success(blogService.changeLikeBlogById(id, blog));
    }

    @PostMapping(path = "/uploadDraft")
    public CommonResult uploadDraft(HttpServletRequest request, @RequestBody Blog blog, @RequestParam(value = "draftId", required = false) String uuid) {
        blog.setTime(new Date());
        User user = findUserByToken(request);
        uuid = blogService.saveDraft(user.getUsername(), uuid, blog);
        return CommonResult.success(uuid);
    }

    @GetMapping(path = "/deleteDraft")
    public CommonResult deleteDraft(HttpServletRequest request, @RequestParam(value = "draftId") String uuid) {
        if (uuid == null) return CommonResult.failed("Draft Id cannot be null");
        User user = findUserByToken(request);
        blogService.deleteDraft(user.getUsername(), uuid);
        return CommonResult.success("删除草稿成功");
    }

    @GetMapping(path = "/draft")
    public CommonResult getDraft(HttpServletRequest request, @RequestParam(value = "draftId") String uuid) {
        String username = findUserByToken(request).getUsername();
        return CommonResult.success(blogService.getDraft(username, uuid));
    }

    @GetMapping(path = "/allDraft")
    public CommonResult getAllDraft(HttpServletRequest request) {
        User user = findUserByToken(request);
        return CommonResult.success(blogService.getAllDrafts(user.getUsername()));
    }

    @GetMapping(path = "/mostRecent")
    public CommonResult getMostRecentFive() {
        return CommonResult.success(blogService.getMostRecentFive());
    }

    private User findUserByToken(HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromToken(request.getHeader(tokenHeader).substring(this.tokenHead.length()));
        return userService.findUserByUserName(username).get(0);
    }

}
