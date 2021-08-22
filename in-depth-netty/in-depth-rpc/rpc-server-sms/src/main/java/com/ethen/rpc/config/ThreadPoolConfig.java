package com.ethen.rpc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig {
    /**
     * io密集型线程池配置
     *
     * @return pool
     */
    @Bean
    public ExecutorService ioPool() {
        int core = Runtime.getRuntime().availableProcessors();
        int max = core * 2 + 2;
        long keepSeconds = 60L;
        int queueCap = 2000;
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(queueCap);
        final ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
        return new ThreadPoolExecutor(core, max, keepSeconds, TimeUnit.SECONDS, queue, callerRunsPolicy);
    }
}
