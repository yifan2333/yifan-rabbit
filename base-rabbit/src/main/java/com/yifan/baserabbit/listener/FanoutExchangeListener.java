package com.yifan.baserabbit.listener;

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
@RabbitListener(queues = "FanoutQueue2")
public class FanoutExchangeListener {

    private static final Logger logger = LoggerFactory.getLogger(Listener.class);

    @RabbitHandler
    public void process(@Payload String foo) {
        logger.info("FanoutExchangeListener: " + foo);
    }
}
