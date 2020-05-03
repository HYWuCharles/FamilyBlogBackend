package com.haoyu.family_blog.Service;

import com.haoyu.family_blog.Model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Haoyu WU on 2020/4/14.
 */
public interface IImagesService {

    String uploadAvatar(MultipartFile file, User user) throws Exception;

    List<String> uploadImages(MultipartFile[] images, String blogId, Long userId);

    List<String> getImages(Long accordedBlog);
}
