package com.haoyu.family_blog.Model;

import java.io.Serializable;
import java.util.Date;

public class Images implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column images.id
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column images.uploaded_by
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    private Long uploadedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column images.time
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    private Date time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column images.link
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    private String link;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column images.accorded_blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    private Long accordedBlog;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table images
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column images.id
     *
     * @return the value of images.id
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column images.id
     *
     * @param id the value for images.id
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column images.uploaded_by
     *
     * @return the value of images.uploaded_by
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    public Long getUploadedBy() {
        return uploadedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column images.uploaded_by
     *
     * @param uploadedBy the value for images.uploaded_by
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    public void setUploadedBy(Long uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column images.time
     *
     * @return the value of images.time
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    public Date getTime() {
        return time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column images.time
     *
     * @param time the value for images.time
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column images.link
     *
     * @return the value of images.link
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    public String getLink() {
        return link;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column images.link
     *
     * @param link the value for images.link
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column images.accorded_blog
     *
     * @return the value of images.accorded_blog
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    public Long getAccordedBlog() {
        return accordedBlog;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column images.accorded_blog
     *
     * @param accordedBlog the value for images.accorded_blog
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    public void setAccordedBlog(Long accordedBlog) {
        this.accordedBlog = accordedBlog;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table images
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uploadedBy=").append(uploadedBy);
        sb.append(", time=").append(time);
        sb.append(", link=").append(link);
        sb.append(", accordedBlog=").append(accordedBlog);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}