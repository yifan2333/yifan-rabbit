package com.yifan.rabbitconsumer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author wuyifan
 * @since 2017年12月20日
 */
@Component
public class FanoutExchangeListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FanoutExchangeListener.class);

    @RabbitListener(queues = "FanoutQueue")
    @RabbitHandler
    public void process(@Payload String foo) {
        LOGGER.info("FanoutExchangeListener: " + foo);
    }
}
