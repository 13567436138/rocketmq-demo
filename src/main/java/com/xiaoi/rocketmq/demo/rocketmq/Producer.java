package com.xiaoi.rocketmq.demo.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

@Component
public class Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @PostConstruct
    public void defaultMQProducer() throws UnsupportedEncodingException {
            for (int i = 0; i < 100; i++) {
                String messageBody = "我是消息内容:" + i;
                String message = new String(messageBody.getBytes(), "utf-8");
                //构建消息

                //Message msg = new Message(topic, tags, "key_" + i /* Keys */, message.getBytes());
                Message<String> msg = MessageBuilder.withPayload(message)
                        .setHeader(MessageConst.PROPERTY_KEYS, "key_" + i )
                        .setHeader("TAGS", "tag")
                        .setHeader(MessageConst.PROPERTY_BUYER_ID,"20191018a")
                        .setHeader(MessageConst.PROPERTY_PRODUCER_GROUP,"group1")
                        .setHeader("MQ", "user_mq")
                        .build();
                //发送消息
                rocketMQTemplate.send("topic1",msg);

            }
    }
}