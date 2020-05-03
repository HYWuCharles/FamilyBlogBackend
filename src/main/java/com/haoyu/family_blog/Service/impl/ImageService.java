package com.haoyu.family_blog.Service.impl;

import com.haoyu.family_blog.Model.Images;
import com.haoyu.family_blog.Model.ImagesExample;
import com.haoyu.family_blog.Model.User;
import com.haoyu.family_blog.Service.IImagesService;
import com.haoyu.family_blog.mapper.ImagesMapper;
import com.haoyu.family_blog.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Haoyu WU on 2020/4/16.
 */
@Service
public class ImageService implements IImagesService {

    SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");

    @Resource
    private UserMapper userMapper;

    @Resource
    private ImagesMapper imagesMapper;

    @Override
    public String uploadAvatar(MultipartFile image, User user) {
        String format = sd.format(new Date());
        try {
            String path = ResourceUtils.getURL("classpath:").getPath() + "static/avatar/";
            File file = new File(path + format);
            if (!file.isDirectory()) {
                file.mkdirs();
            }
            String oldName = image.getOriginalFilename();
            String newName = UUID.randomUUID().toString() + oldName.substring(oldName.indexOf("."), oldName.length());
            image.transferTo(new File(file, newName));
            String staticPath = "/avatar/" + format + "/" + newName;
            user.setAvatar(staticPath);
            userMapper.updateByPrimaryKeySelective(user);
            return staticPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> uploadImages(MultipartFile[] images, String blogId, Long userId) {
        String format = sd.format(new Date());
        List<String> imagePaths = new ArrayList<>();
        try {
            for (int i = 0; i < images.length; i++) {
                String path = ResourceUtils.getURL("classpath:").getPath() + "static/blogImages/";
                MultipartFile image = images[i];
                File file = new File(path + format);
                if (!file.isDirectory()) {
                    file.mkdirs();
                }
                String oldName = image.getOriginalFilename();
                String newName = UUID.randomUUID().toString() + oldName.substring(oldName.indexOf("."), oldName.length());
                image.transferTo(new File(file, newName));
                String staticPath = "/blogImages/" + format + "/" + newName;
                Images imageToDB = new Images();
                imageToDB.setUploadedBy(userId);
                imageToDB.setTime(new Date());
                imageToDB.setAccordedBlog(Long.parseLong(blogId));
                imageToDB.setLink(staticPath);
                imagesMapper.insertSelective(imageToDB);
                imagePaths.add(staticPath);
            }
            return imagePaths;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getImages(Long accordedBlog) {
        ImagesExample imagesExample = new ImagesExample();
        imagesExample.createCriteria().andAccordedBlogEqualTo(accordedBlog);
        List<String> links = new ArrayList<>();
        List<Images> images = imagesMapper.selectByExample(imagesExample);
        for (Images image : images) {
            links.add(image.getLink());
        }
        return links;
    }
}
