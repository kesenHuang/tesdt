package com.kesen.tech.messagegateway;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageGateWayApplication.class)
public class MessageGateWayApplicationTests {

    @Resource
    private RestTemplate restTemplate;

    @Test
    public void contextLoads() {
    }


    @Test
    public void distrubtedLock() throws InterruptedException {
        String url = "http://localhost:8080/distributedLock";
        String uuid = "abcdefg";
//        log.info("uuid:{}", uuid);
        String key = "redisLock";
        String secondsToLive = "10";

        for (int i = 0; i < 100; i++) {
            final int userId = i;

            Thread thread = new Thread(() -> {
                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.add("uuid", uuid);
                params.add("key", key);
                params.add("secondsToLock", secondsToLive);
                params.add("userId", String.valueOf(userId));
                String result = restTemplate.postForObject(url, params, String.class);
                System.out.println("-------------" + result);
            }
            );
            thread.start();
        }

    }


    @Test
    public void limitTest() throws InterruptedException {
        String url = "http://localhost:8080/distributedLimit";

        for (int i = 0; i < 100; i++) {
            final int userId = i;

            Thread thread = new Thread(() -> {
                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.add("userId", String.valueOf(userId));
                String result = restTemplate.postForObject(url, params, String.class);
                System.out.println("-------------" + result);
            }
            );
            Thread.sleep(1000);
            thread.start();
        }

        Thread.sleep(10000);

    }
}
