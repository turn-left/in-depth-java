package com.ethen.rocket.producer;

import com.ethen.rocket.RocketConstant;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * 消息同步发送
 */
public class SyncProducer {
    private static final String ORDER_GROUP = "order_producer_group_dev";

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, MQBrokerException, RemotingException, InterruptedException {
        final DefaultMQProducer producer = new DefaultMQProducer(ORDER_GROUP);
        producer.setNamesrvAddr(RocketConstant.NAME_SERVER_ADDR);
        producer.start();

        for (int i = 0; i < 100; i++) {
            String msgContent = "Hello,Rocket-" + i;
            final Message message = new Message(RocketConstant.TOPIC_TEST, RocketConstant.SHIRT_ORDER_TAG,
                    msgContent.getBytes(RemotingHelper.DEFAULT_CHARSET));
            final SendResult result = producer.send(message);
            System.err.printf("%s%n", result);
        }

        producer.shutdown();

    }
}
