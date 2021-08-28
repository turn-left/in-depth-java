package com.ethen.netty.pkg;
/**
 * TCP粘包拆包demo
 * <p>
 * Nagle算法
 * Nagle算法的规则（可参考tcp_output.c文件里tcp_nagle_check函数注释）：
 * （1）如果包长度达到MSS，则允许发送；
 * （2）如果该包含有FIN，则允许发送；
 * （3）设置了TCP_NODELAY选项，则允许发送；
 * （4）未设置TCP_CORK选项时，若所有发出去的小数据包（包长度小于MSS）均被确认，则允许发送；
 * （5）上述条件都未满足，但发生了超时（一般为200ms），则立即发送。
 * <p>
 * Nagle算法伪代码：
 *    #有数据要发送
 *    if there is new data to send
 *         # 发送窗口缓冲区和队列数据 >=mss，队列数据（available data）为原有的队列数据加上新到来的数据
 *         # 也就是说缓冲区数据超过mss大小，nagle算法尽可能发送足够大的数据包
 *         if the window size >= MSS and available data is >= MSS
 *             send complete MSS segment now # 立即发送
 *         else
 *             if there is unconfirmed data still in the pipe # 前一次发送的包没有收到ack
 *                 # 将该包数据放入队列中，直到收到一个ack再发送缓冲区数据
 *                 enqueue data in the buffer until an acknowledge is received
 *             else
 *                 send data immediately # 立即发送
 *             end if
 *         end if
 *     end if　
 *
 *
 */