package com.kesen.tech.messagegateway.redis;

import com.kesen.tech.messagegateway.common.constant.Constants;
import com.kesen.tech.messagegateway.common.enums.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @className: com.kesen.tech.messagegateway.redis-> RedisLimitClient
 * @description: 限流
 * @author: kesen
 * @createDate: 2022-01-05 11:30
 * @version: 1.0
 */
@Component
@Slf4j
public class RedisLimitClient {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Qualifier("getRedisScript")
    @Resource
    RedisScript<Long> ratelimitLua;

    @Qualifier("getInitRedisScript")
    @Resource
    RedisScript<Long> ratelimitInitLua;


    /**
     * last_mill_second 最后时间毫秒
     * curr_permits 当前可用的令牌
     * max_burst 令牌桶最大值
     * rate 每秒生成几个令牌
     * app 应用
     * @param key
     * @return
     */
    public Token initToken(String key){
        Token token;
        Long currMillSecond = stringRedisTemplate.execute(
                RedisServerCommands::time
        );
        /**
         * redis.pcall("HMSET",KEYS[1],
         "last_mill_second",ARGV[1],
         "curr_permits",ARGV[2],
         "max_burst",ARGV[3],
         "rate",ARGV[4],
         "app",ARGV[5])
         */
        Long accquire = stringRedisTemplate.execute(ratelimitInitLua,
                Collections.singletonList(getKey(key)), currMillSecond.toString(), "1", "10", "10", "skynet");
        if (null == accquire) {
            token = Token.FAILED;
            return token;
        }
        if (accquire == 1) {
            token = Token.SUCCESS;
        } else if (accquire == 0) {
            token = Token.SUCCESS;
        } else {
            token = Token.FAILED;
        }
        return token;
    }


    public String getKey(String key) {
        return Constants.RATE_LIMIT_KEY + key;
    }
}
