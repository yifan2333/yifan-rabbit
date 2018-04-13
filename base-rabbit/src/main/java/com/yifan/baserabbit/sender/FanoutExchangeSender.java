package com.yifan.baserabbit.sender;

import com.yifan.baserabbit.config.AmqpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author wuyifan
 * @since 2017年12月20日
 */
@Component
public class FanoutExchangeSender implements RabbitTemplate.ConfirmCallback {


    private static final Logger LOGGER = LoggerFactory.getLogger(FanoutExchangeSender.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public FanoutExchangeSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }

    public void send(String msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        LOGGER.info("send: " + correlationData.getId() + "msg: " + msg);
        rabbitTemplate.convertAndSend("", "");
        this.rabbitTemplate.convertAndSend("FanoutExchange", "", msg, correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        LOGGER.info("confirm: {}, ack {}, cause {}",correlationData.getId(), ack, cause);
    }

}
