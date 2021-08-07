package com.ethen.chap01;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Collectors;

public class UseJedis {
    final static String USER_ORDER_KEY_PATTERN = "user:%s:order";
    final static String ORDER_KEY_PATTERN = "order:%s";
    public static final String REDIS_HOST = "192.168.10.100";
    public static final int REDIS_PORT = 6379;
    public static final String REDIS_SECRET = "123";
    Jedis jedis = null;


    @Before
    public void init() {
        jedis = new Jedis(REDIS_HOST, REDIS_PORT);
        String auth = jedis.auth(REDIS_SECRET);
        System.err.println(auth);
    }

    @Test
    public void putOrderContent() {
        // 每个用户有多个订单key为 order:1 order:2 order:3 结合hmset
        // 将订单内容以Hash结构放入redis
        String order1 = jedis.hmset("order:1", getOrderMap("1", "36.6", "2018-01-01"));
        String order2 = jedis.hmset("order:2", getOrderMap("2", "46.6", "2018-11-01"));
        String order3 = jedis.hmset("order:3", getOrderMap("3", "56.6", "2018-6-01"));
    }

    @Test
    public void putOrderKeys() {
        // 将订单Key放到队列
        jedis.lpush("user:1:order", "order:1", "order:2", "order:3");
    }

    @Test
    public void addNewOrder() {
        // 新产生了一个订单order:4 追加一个order:4放入队列第一个位置
        jedis.hmset("order:4", getOrderMap("4", "998", "2021-9-15"));
        jedis.lpush("user:1:order", "order:4");
    }

    @Test
    public void userOderGet() {
        List<Map<String, String>> userOrderInfo = getUserOrderInfo("1");
        System.err.println("user:1 order info " + userOrderInfo);
    }

    public List<Map<String, String>> getUserOrderInfo(String userId) {
        String userKey = String.format(Locale.ENGLISH, USER_ORDER_KEY_PATTERN, userId);
        List<String> orderList = jedis.lrange(userKey, 0, -1).stream().distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(orderList)) return null;
        LinkedList<Map<String, String>> orders = new LinkedList<>();

        orderList.forEach(item -> {
            Map<String, String> orderMap = jedis.hgetAll(item);
            System.out.println("get order map " + orderMap);
            orders.add(orderMap);
        });
        return orders;
    }


    public static Map<String, String> getOrderMap(String orderId, String money, String date) {
        HashMap<String, String> order = new HashMap<>();
        order.put("orderId", orderId);
        order.put("money", money);
        order.put("time", date);
        return order;
    }
}
