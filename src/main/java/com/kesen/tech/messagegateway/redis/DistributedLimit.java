package com.kesen.tech.messagegateway.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @className: com.kesen.tech.messagegateway.redis-> DistributedLimit
 * @description: 分布式限流
 * @author: kesen
 * @createDate: 2022-01-04 17:27
 * @version: 1.0
 */
@Slf4j
@Component
public class DistributedLimit {

    //注意RedisTemplate用的String,String，后续所有用到的key和value都是String的
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Autowired
    RedisScript<Long> limitScript;

    public Boolean distributedLimit(String key, String limit) {
        Long id = 0L;

        try {
            id = redisTemplate.execute(limitScript, Collections.singletonList(key), limit);
           // log.info("id:{}", id);
        } catch (Exception e) {
            log.error("error", e);
        }

        if (id == 0L) {
            return false;
        } else {
            return true;
        }
    }
}
