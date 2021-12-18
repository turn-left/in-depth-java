package com.ethen.rocket.producer;

import com.ethen.rocket.RocketConstant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * 异步生产消息
 */
public class AsyncProducer {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        // 实例化生产者
        boolean enableTrace = true;
        final DefaultMQProducer producer = new DefaultMQProducer("AsyncProducer", enableTrace);
        // 设置NameServer地址
        producer.setNamesrvAddr(RocketConstant.NAME_SERVER_ADDR);
        // 启动生产者
        producer.start();
        // 失败不重试
        producer.setRetryTimesWhenSendAsyncFailed(0);
        int messageCount = 100;
        final CountDownLatch2 countDownLatch2 = new CountDownLatch2(messageCount);
        for (int i = 0; i < messageCount; i++) {
            final int index = i;
            String msgBody = "Hello world,rocket AsyncProducer-" + i;
            String asyncProducer = "AsyncProducerTag";
            final Message message = new Message(RocketConstant.TOPIC_TEST, asyncProducer, msgBody.getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 异步发送消息
            producer.send(message, new SendCallback() {
                // 成功发送
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch2.countDown();
                    System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                }

                // 发送失败
                @Override
                public void onException(Throwable e) {
                    countDownLatch2.countDown();
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        // 等待5秒
        countDownLatch2.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}
