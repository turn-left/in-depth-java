package com.ethen.chap01;

import java.nio.ByteBuffer;

/**
 * description:
 *
 * @author ethenyang@126.com
 * @since 2021/07/01
 */
public class OutOfMemoryTest {
    public static void main(String[] args) {
        final ByteBuffer allocate = ByteBuffer.allocate(8 * 1024 * 1024);
    }
}
