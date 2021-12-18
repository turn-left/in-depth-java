package com.ethen.rocket.consumer;

import com.ethen.rocket.RocketConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 消息消费
 */
public class SimpleConsumer {
    public static void main(String[] args) throws MQClientException {

        String consumerGroup = "simple_group";
        String topic = "log_topic";
        final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(RocketConstant.NAME_SERVER_ADDR);
        // 订阅主题，以及tag过滤
        consumer.subscribe(topic, "*");
        // 注册回调实现类，处理从Broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages:%s %n", Thread.currentThread().getName(), list);
                // fixme 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
