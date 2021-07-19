package com.ethen.chap02.contools;

import com.ethen.common.DbConnectionImpl;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * 信号量使用极其注意事项
 * 模拟最多允许10个连接同时访问数据库
 * <p>
 * fixme 连接池实际须做成单例，并且做并发控制
 */
public class UseSemaphoreAsPool {
    private static LinkedList<DbConnectionImpl> pool = new LinkedList<>();
    private static Semaphore used, unused;

    public static final int ACCESS_TIMES = 50;

    static {
        used = new Semaphore(0);
        unused = new Semaphore(10);
    }

    @AllArgsConstructor
    static class AccessDB implements Runnable {
        private UseSemaphoreAsPool connPool;

        @Override
        public void run() {
            for (int k = 0; k < ACCESS_TIMES; k++) {
                System.err.println(Thread.currentThread().getName() + "开始访问DB" + "-" + unused.availablePermits());
                DbConnectionImpl conn = connPool.getConnection();
                if (conn == null) {
                    System.err.println(Thread.currentThread().getName() + "-未获取到连接！！！");
                    return;
                }
                String stmt = "select * from dual";
                conn.prepareStatement(stmt);
                conn.commit();
                connPool.releaseConnection(DbConnectionImpl.fetchConnection());
                System.err.println(Thread.currentThread().getName() + "归还连接" + "-" + unused.availablePermits());
            }
        }
    }

    public static void main(String[] args) {
        UseSemaphoreAsPool pool = new UseSemaphoreAsPool(10);
//        Runnable accessDb = () -> {
//            System.err.println(Thread.currentThread().getName() + "开始访问DB" + "-" + unused.availablePermits());
//            DbConnectionImpl conn = pool.getConnection();
//            String stmt = "select * from dual";
//            conn.prepareStatement(stmt);
//            conn.commit();
//            pool.releaseConnection(conn);
//            System.err.println(Thread.currentThread().getName() + "归还连接" + "-" + unused.availablePermits());
//        };
        for (int i = 0; i < 50; i++) {
            new Thread(new AccessDB(pool)).start();
        }
    }

    public UseSemaphoreAsPool(int initialSize) {
        if (initialSize <= 0) throw new IllegalArgumentException();
        for (int i = 0; i < initialSize; i++)
            pool.add(DbConnectionImpl.fetchConnection());
    }

    @SneakyThrows
    public DbConnectionImpl getConnection() {
        DbConnectionImpl conn = null;
        synchronized (pool) {
            if (!pool.isEmpty()) {
                unused.acquire();
                conn = pool.removeFirst();
                System.err.println(Thread.currentThread() + "取得连接：" + conn);
            }
            return conn;
        }
    }

    public void releaseConnection(Connection conn) {
        synchronized (pool) {
            if (conn != null) {
                pool.addLast((DbConnectionImpl) conn);
                unused.release();
            }
        }
    }


}
