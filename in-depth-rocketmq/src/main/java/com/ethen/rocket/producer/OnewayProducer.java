package com.ethen.rocket.producer;

import com.ethen.rocket.RocketConstant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * 单向发送 比如日志发送
 */
public class OnewayProducer {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        String topic = "log_topic";
        boolean enableTrace = true;
        final DefaultMQProducer producer = new DefaultMQProducer(topic, enableTrace);
        producer.setNamesrvAddr(RocketConstant.NAME_SERVER_ADDR);
        producer.start();
        String tag = "oneway_log_tag";
        for (int i = 0; i < 100; i++) {
            byte[] msgContent = ("invoke OnewayProducer#sendMessage-" + i).getBytes(RemotingHelper.DEFAULT_CHARSET);
            final Message message = new Message(topic, tag, msgContent);
            producer.sendOneway(message);
        }
        producer.shutdown();
    }
}
