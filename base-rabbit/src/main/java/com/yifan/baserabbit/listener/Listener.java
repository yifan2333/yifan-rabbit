package com.yifan.baserabbit.listener;

import com.yifan.baserabbit.config.AmqpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;


/**
 * @author wuyifan
 * @since 2017年12月20日
 */
@Configuration
public class Listener {
    private static final Logger logger = LoggerFactory.getLogger(Listener.class);

    @RabbitListener(queues = AmqpConfig.FOO_QUEUE)
    @RabbitHandler
    public void process(Message message) throws UnsupportedEncodingException {

        String foo = new String(message.getBody(), "utf-8");

        logger.info("Consumer Listener: foo: " + foo + "message" + message);

    }

}
