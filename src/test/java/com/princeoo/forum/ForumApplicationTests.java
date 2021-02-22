package com.princeoo.forum;

import com.princeoo.forum.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ForumApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {
        redisUtil.set("key","测试String");
        System.out.println(redisUtil.get("key"));
    }

}
