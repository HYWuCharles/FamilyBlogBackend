package com.haoyu.family_blog.mapper;

import com.haoyu.family_blog.Model.Blog;
import com.haoyu.family_blog.Model.BlogExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface BlogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @SelectProvider(type = BlogSqlProvider.class, method = "countByExample")
    int countByExample(BlogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @DeleteProvider(type = BlogSqlProvider.class, method = "deleteByExample")
    int deleteByExample(BlogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @Delete({
            "delete from blog",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @Insert({
            "insert into blog (created_by, time, ",
            "hearts, title, content)",
            "values (#{createdBy,jdbcType=BIGINT}, #{time,jdbcType=TIMESTAMP}, ",
            "#{hearts,jdbcType=INTEGER}, #{title,jdbcType=VARCHAR}, #{content,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    int insert(Blog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @InsertProvider(type = BlogSqlProvider.class, method = "insertSelective")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    int insertSelective(Blog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @SelectProvider(type = BlogSqlProvider.class, method = "selectByExampleWithBLOBs")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "created_by", property = "createdBy", jdbcType = JdbcType.BIGINT),
            @Result(column = "time", property = "time", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "hearts", property = "hearts", jdbcType = JdbcType.INTEGER),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.LONGVARCHAR)
    })
    List<Blog> selectByExampleWithBLOBs(BlogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @SelectProvider(type = BlogSqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "created_by", property = "createdBy", jdbcType = JdbcType.BIGINT),
            @Result(column = "time", property = "time", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "hearts", property = "hearts", jdbcType = JdbcType.INTEGER),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR)
    })
    List<Blog> selectByExample(BlogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @Select({
            "select",
            "id, created_by, time, hearts, title, content",
            "from blog",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "created_by", property = "createdBy", jdbcType = JdbcType.BIGINT),
            @Result(column = "time", property = "time", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "hearts", property = "hearts", jdbcType = JdbcType.INTEGER),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.LONGVARCHAR)
    })
    Blog selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @UpdateProvider(type = BlogSqlProvider.class, method = "updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Blog record, @Param("example") BlogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @UpdateProvider(type = BlogSqlProvider.class, method = "updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Blog record, @Param("example") BlogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @UpdateProvider(type = BlogSqlProvider.class, method = "updateByExample")
    int updateByExample(@Param("record") Blog record, @Param("example") BlogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @UpdateProvider(type = BlogSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Blog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @Update({
            "update blog",
            "set created_by = #{createdBy,jdbcType=BIGINT},",
            "time = #{time,jdbcType=TIMESTAMP},",
            "hearts = #{hearts,jdbcType=INTEGER},",
            "title = #{title,jdbcType=VARCHAR},",
            "content = #{content,jdbcType=LONGVARCHAR}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Blog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blog
     *
     * @mbggenerated Fri Apr 24 08:57:56 CST 2020
     */
    @Update({
            "update blog",
            "set created_by = #{createdBy,jdbcType=BIGINT},",
            "time = #{time,jdbcType=TIMESTAMP},",
            "hearts = #{hearts,jdbcType=INTEGER},",
            "title = #{title,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Blog record);
}