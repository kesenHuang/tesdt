package com.kesen.tech.messagegateway.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @className: com.kesen.tech.messagegateway.redis-> DistributedLock
 * @description: 分布式锁
 * @author: kesen
 * @createDate: 2022-01-04 15:53
 * @version: 1.0
 */
@Slf4j
@Component
public class DistributedLock {


    //注意RedisTemplate用的String,String，后续所有用到的key和value都是String的
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    RedisScript<Boolean> lockScript;

    @Autowired
    RedisScript<Long> unlockScript;

    public Boolean distributedLock(String key, String uuid, String secondsToLock) {
        Boolean locked = false;
        try {
            String millSeconds = String.valueOf(Integer.parseInt(secondsToLock) * 1000);
            locked =redisTemplate.execute(lockScript, Collections.singletonList(key), uuid, millSeconds);
            log.info("distributedLock.key{}: - uuid:{}: - timeToLock:{} - locked:{} - millSeconds:{}",
                    key, uuid, secondsToLock, locked, millSeconds);
        } catch (Exception e) {
            log.error("error", e);
        }
        return locked;
    }

    public void distributedUnlock(String key, String uuid) {
        Long unlocked = redisTemplate.execute(unlockScript, Collections.singletonList(key),
                uuid);
        log.info("distributedLock.key{}: - uuid:{}: - unlocked:{}", key, uuid, unlocked);

    }
}
