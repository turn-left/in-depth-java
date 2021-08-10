package com.ethen.chap01;

import com.ethen.model.UserInfo;
import com.ethen.utils.NoNameUtils;
import com.ethen.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JedisConnectionFactory factory;

    @PostMapping
    public Object register(@RequestBody UserInfo userInfo) {
        String uuid = StringUtils.uuid();
        Jedis client = NoNameUtils.getClient(factory);
        userInfo.setUserId(uuid);
        Map<String, String> userMap = new HashMap<>();
        String result = client.hmset("user:" + uuid, userMap);
        log.info("register user {} result {}", userMap, result);
        return result;
    }
}
