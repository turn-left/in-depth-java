package com.ethen.chap02.yang;

import com.ethen.chap02.yang.util.FileUtils;
import com.ethen.chap02.yang.util.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 使用redis实现分布式锁
 * note>
 * 过期时间-->防止死锁
 * 随机串-->防止误解锁
 * lua脚本-->保证解锁过程的原子性
 * 步骤>
 * 1.尝试获取锁
 * 2.成功获取则进行业务逻辑
 * 3.不成功自旋，直至获取锁
 * 4.解锁
 * fixme jedis改进!!!
 */
@Slf4j
public class RedisLock implements Lock {
    static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private final String key;
    private final long timeout;
    private final long polling;
    private final Jedis jedis;

    public RedisLock(String key, long timeout, long polling) {
        assert timeout > 0;
        assert polling > 0;
        this.key = key;
        this.timeout = timeout;
        this.polling = polling;
        jedis = JedisUtils.getClient();
    }

    @Override
    public void lock() {
        if (!tryLock()) {
            try {
                // 间歇性自旋
                TimeUnit.MILLISECONDS.sleep(polling);
                lock();
            } catch (InterruptedException e) {
                // interrupted
                log.warn(Thread.currentThread().getName() + "interrupted");
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock() {
        String uuid = UUID.randomUUID().toString();

        String result = jedis.set(key, uuid, SetParams.setParams().nx().px(timeout));
        if ("OK".equalsIgnoreCase(result)) {
            // 枪锁成功
            threadLocal.set(uuid);
            return true;
        }
        // 该key已经存在->锁已被其他线程抢占
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unlock() {
       /*
        fixme 不具有原子性，存在错误解锁的可能！
        String value = jedis.get(key);
        if (value != null && value.equals(threadLocal.get())) {
            jedis.del(key);
        }
       */
        String script = FileUtils.getScript("scripts/unlock.lua");
        // 使用lua脚本确保解锁过程的原子性
        jedis.eval(script, Collections.singletonList(key), Collections.singletonList(threadLocal.get()));
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
