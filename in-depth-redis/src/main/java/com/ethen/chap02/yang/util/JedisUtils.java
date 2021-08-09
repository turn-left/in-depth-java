package com.ethen.chap02.yang.util;

import redis.clients.jedis.Jedis;

public class JedisUtils {
    public static final String REDIS_HOST = "192.168.10.100";
    public static final int REDIS_PORT = 6379;
    public static final String REDIS_SECRET = "123";


    public static Jedis getClient() {
        Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT);
        String auth = jedis.auth(REDIS_SECRET);
        System.out.println("auth:" + auth);
        return jedis;
    }
}
