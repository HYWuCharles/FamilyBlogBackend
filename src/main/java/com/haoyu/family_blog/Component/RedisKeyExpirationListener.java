package com.haoyu.family_blog.Component;

import com.haoyu.family_blog.ScheduledTasks.DraftsRedisClearance;
import com.haoyu.family_blog.Service.impl.RedisService;
import com.haoyu.family_blog.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by Haoyu WU on 2020/4/26.
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    private Logger LOGGER = LoggerFactory.getLogger(DraftsRedisClearance.class);

    @Value(value = "${redis.cache.draft.firstlevel.prefix}")
    private String draftFirstLevelPrefix;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisService redisService;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        LOGGER.info("New message {}", message.toString());
        if (expiredKey.startsWith(draftFirstLevelPrefix)) {
            LOGGER.info("Key: " + expiredKey + " expired, doing post-expiration clearance");
            String username = expiredKey.substring(expiredKey.indexOf(":") + 1, expiredKey.lastIndexOf(":"));
            Set<String> firstLevel = redisService.getSet(username);
            firstLevel.remove(expiredKey.substring(draftFirstLevelPrefix.length() + username.length() + 1));
            if (firstLevel.isEmpty()) redisService.remove(username);
            LOGGER.info("Expired key: " + expiredKey + " clearance done");
        }
    }
}
