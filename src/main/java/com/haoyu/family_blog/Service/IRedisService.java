package com.haoyu.family_blog.Service;

import java.util.HashMap;
import java.util.Set;

/**
 * Redis Operation Service
 * Created by Haoyu WU on 2020/4/16.
 */
public interface IRedisService {
    /**
     * Store value into Redis
     *
     * @param key   uuid
     * @param value verification code
     */
    void set(String key, String value);

    /**
     * @param key uuid
     * @return value
     */
    String get(String key);

    /**
     * @param key uuid
     * @param exp expired time
     * @return whether success or not
     */
    boolean expire(String key, long exp);

    /**
     * @param key uuid
     */
    void remove(String key);

    void setAllHash(String key, HashMap<String, Object> hashMap);

    void setSet(String userName, String value);

    Set<String> getSet(String userName);

    boolean isEmptySet(String userName);

    void deleteSetElement(String userName, String value);

    void remove(Long id);

    void setSingleHashValue(String key, String hashKey, Object value);

    HashMap<String, Object> getAllHash(String key);

    void deleteHash(String key);

    boolean hasHashMap(String key);
}
