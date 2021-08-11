package com.ethen.chap01;

import com.ethen.chap02.yang.util.JedisUtils;
import com.ethen.utils.NoNameUtils;
import com.ethen.utils.StringUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

/**
 * 用户--> 用户微博(朋友圈、说说)动态 -->点赞
 * 用户-->动态 （list）
 * 动态-->点赞 (zset)
 */
@Api("使用redis实现点赞功能")
@RestController
@RequestMapping("/dianzan")
public class DianzanController {
    @Autowired
    private JedisConnectionFactory factory;

    /**
     * 用户给朋友圈动态点赞
     *
     * @param articleId
     * @param userId
     * @return
     */
    @PutMapping("/article/{aid}/user/{uid}")
    public Object dianzan(@PathVariable("aid") String articleId, String userId) {
        Jedis client = JedisUtils.getClient(factory);
        Long res = client.zadd(StringUtils.zanKey(articleId), System.currentTimeMillis(), userId);
        return res;
    }
}
