package com.kesen.tech.messagegateway.controller;

import com.kesen.tech.messagegateway.anno.DistriLimitAnno;
import com.kesen.tech.messagegateway.common.pojo.CommonResult;
import com.kesen.tech.messagegateway.redis.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @className: com.kesen.tech.messagegateway.controller-> LockController
 * @description:
 * @author: kesen
 * @createDate: 2022-01-04 16:04
 * @version: 1.0
 */
@RestController
@Slf4j
public class LockController {


    @Autowired
    private DistributedLock lock;



    @PostMapping("/distributedLock")
    public String distributedLock(String key, String uuid, String secondsToLock, String userId) throws Exception{
//        String uuid = UUID.randomUUID().toString();
        Boolean locked = false;
        try {
            locked = lock.distributedLock(key, uuid, secondsToLock);
            if(locked) {
                log.info("userId:{} is locked - uuid:{}", userId, uuid);
                log.info("do business logic");
                TimeUnit.MICROSECONDS.sleep(3000);
            } else {
                log.info("userId:{} is not locked - uuid:{}", userId, uuid);
            }
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            if(locked) {
                lock.distributedUnlock(key, uuid);
            }
        }

        return "ok";
    }


    @PostMapping("/distributedLimit")
    @DistriLimitAnno(limitKey="limit", limit = 10)
    public CommonResult<String> distributedLimit(String userId) {
        log.info(userId);
        return CommonResult.success("ok");
    }
}
