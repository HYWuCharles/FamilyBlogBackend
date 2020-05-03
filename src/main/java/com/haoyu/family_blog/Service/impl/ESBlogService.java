package com.haoyu.family_blog.Service.impl;

import com.haoyu.family_blog.NoSQL.elastic.dao.impl.DaoEsBlogs;
import com.haoyu.family_blog.NoSQL.elastic.document.ESBlog;
import com.haoyu.family_blog.NoSQL.elastic.repository.ESBlogRepository;
import com.haoyu.family_blog.Service.IESBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Haoyu WU on 2020/4/24.
 */
@Service
public class ESBlogService implements IESBlogService {
    @Autowired
    ESBlogRepository esBlogRepository;
    @Autowired
    DaoEsBlogs daoEsBlogs;

    @Override
    public int importAll() {
        List<ESBlog> esBlogs = daoEsBlogs.findAllElasticBlogs();
        Iterable<ESBlog> esBlogIterable = esBlogRepository.saveAll(esBlogs);
        Iterator<ESBlog> iterator = esBlogIterable.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            count++;
            iterator.next();
        }
        return count;
    }

    @Override
    public void deleteEsBlog(Long id) {
        esBlogRepository.deleteById(id);
    }

    @Override
    public ESBlog createEsBlog(Long id) {
        ESBlog esBlog = daoEsBlogs.findElasticBlogById(id);
        if (esBlog != null) {
            esBlogRepository.save(esBlog);
        }
        return esBlog;
    }

    @Override
    public void delete(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<ESBlog> esProductList = new ArrayList<>();
            for (Long id : ids) {
                ESBlog esProduct = new ESBlog();
                esProduct.setId(id);
                esProductList.add(esProduct);
            }
            esBlogRepository.deleteAll(esProductList);
        }
    }

    @Override
    public Page<ESBlog> search(String title, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esBlogRepository.findESBlogByTitle(title, pageable);
    }
}
