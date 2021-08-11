package com.ethen.usecase;

import com.alibaba.fastjson.JSONObject;
import com.ethen.chap02.yang.util.JedisUtils;
import com.ethen.config.CurrentLimitProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 使用redis做限流
 * 实现访问者 $ip 127.0.0.1在一定的时间 $time 20S内只能访问 $limit 10次.
 * v1.0 fixme 散装redis命令实现,操作不满足原子性
 */
@Slf4j
public class CurrentLimitFilter implements Filter {
    private final JedisConnectionFactory factory;
    private final int duration;
    private final int limitTimes;
    static final String LIMIT_IP_KEY = "limit:ip:%s";

    public CurrentLimitFilter(JedisConnectionFactory factory, int duration, int limitTimes) {
        this.factory = factory;
        this.duration = duration;
        this.limitTimes = limitTimes;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init CurrentLimitFilter ...");
    }

    @Override
    public void destroy() {
        log.info("destroy CurrentLimitFilter ...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String remoteHost = request.getRemoteHost();
        Jedis client = JedisUtils.getClient(factory);
        String key = String.format(LIMIT_IP_KEY, remoteHost);
        if (client.exists(key) && client.incr(key) > limitTimes) {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(403);
            res.setContentType("application/json; charset=utf-8");
            res.setCharacterEncoding("UTF-8");
            try (OutputStream out = res.getOutputStream()) {
                String retContent = "{'code':403,'msg':'access denied!visit too often!'}";
                out.write(retContent.getBytes(StandardCharsets.UTF_8));
                out.flush();
            }
        } else {
            client.incr(key);
            client.expire(key, duration);
            chain.doFilter(request, response);
        }
    }
}
