package com.ethen.rocket.producer;

import com.ethen.rocket.RocketConstant;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * 延时消息
 */
public class DelayProducer {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, MQBrokerException, RemotingException, InterruptedException {
        String topic = "log_topic";
        String tag = "delay_tag";
        final DefaultMQProducer producer = new DefaultMQProducer("DelayProducerGroup");
        producer.setNamesrvAddr(RocketConstant.NAME_SERVER_ADDR);
        producer.start();
        int totleMsgCount = 100;
        for (int i = 0; i < totleMsgCount; i++) {
            final byte[] msgContent = ("Hello delay Message-" + i).getBytes(RemotingHelper.DEFAULT_CHARSET);
            final Message message = new Message(topic, tag, msgContent);
            // 设置延迟等级为3，消息将在10秒之后发送
            message.setDelayTimeLevel(3);
            producer.send(message);
        }
        producer.shutdown();
    }
}
