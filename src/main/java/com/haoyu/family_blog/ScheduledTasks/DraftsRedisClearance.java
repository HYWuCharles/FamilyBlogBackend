package com.haoyu.family_blog.ScheduledTasks;

/**
 * Created by Haoyu WU on 2020/4/26.
 */

public class DraftsRedisClearance {
    /*
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisService redisService;

    private Logger LOGGER = LoggerFactory.getLogger(DraftsRedisClearance.class);

    @Scheduled(cron = "0/20 * * ? * ?")
    private void clearRedisFirstLevelDraft() {
        LOGGER.info("Trigger new draft redis draft clearance task");
        UserExample userExample = new UserExample();
        List<User> users = userMapper.selectByExample(userExample);
        for (User user: users) {
            Set<String> firstLevel = redisService.getSet(user.getUsername());
            // System.out.println("User: " + user.getUsername() + " first level set size is " + firstLevel.size());
            if (firstLevel.isEmpty()) redisService.remove(user.getId());
        }
    }

     */
}
