package com.haoyu.family_blog.NoSQL.elastic.document;

import com.haoyu.family_blog.Model.Blog;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Haoyu WU on 2020/4/24.
 */
@Document(indexName = "family_blog", type = "blog", shards = 5, replicas = 0)
public class ESBlog implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    private Long id;
    @Field(type = FieldType.Long)
    private Long createdBy;
    @Field(type = FieldType.Date)
    private Date time;
    @Field(type = FieldType.Integer)
    private Integer hearts;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Keyword)
    private String content;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ESBlog() {
    }

    public ESBlog(Blog blog) {
        this.setTitle(blog.getTitle());
        this.setContent(blog.getContent());
        this.setCreatedBy(blog.getCreatedBy());
        this.setHearts(blog.getHearts());
        this.setId(blog.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getHearts() {
        return hearts;
    }

    public void setHearts(Integer hearts) {
        this.hearts = hearts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
