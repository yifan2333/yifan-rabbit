package com.yifan.rabbitconsumer.listener;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;

/**
 * @author wuyifan
 * @since 2017年12月20日
 */
@Configuration
public class Listener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    @RabbitListener(queues = "callback.queue.foo")
    @RabbitHandler
    public void process(Message message) throws UnsupportedEncodingException {

        String foo = new String(message.getBody(), "utf-8");

        LOGGER.info("Consumer Listener: foo: " + foo + "messageId" + message.getMessageProperties().getMessageId());

        LOGGER.info("MessageProperties" + JSONObject.toJSONString(message.getMessageProperties()));
    }

}
