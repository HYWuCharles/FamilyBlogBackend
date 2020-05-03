package com.haoyu.family_blog;

import org.junit.jupiter.api.Test;

/**
 * Created by Haoyu WU on 2020/4/26.
 */

public class RedisTest {

    @Test
    public void test() {
        String a = "Draft:haoyu:143lajlfalkutoiwqjer";
        String prefix = "Draft:";
        String username = a.substring(a.indexOf(":") + 1, a.lastIndexOf(":"));
        System.out.println(username);
        System.out.println(a.substring(prefix.length() + username.length() + 1));
    }
}
