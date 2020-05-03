package com.haoyu.family_blog.Config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Haoyu WU on 2020/4/14.
 * MyBatis configuration class
 */
@Configuration
@MapperScan(basePackages = {"com.haoyu.family_blog.mapper"})
public class MyBatisScanner {
}
