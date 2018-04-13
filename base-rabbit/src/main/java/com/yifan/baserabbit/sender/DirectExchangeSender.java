package com.yifan.baserabbit.sender;

import com.yifan.baserabbit.config.AmqpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author wuyifan
 * @since 2017年12月20日
 */
@Component
public class DirectExchangeSender implements RabbitTemplate.ConfirmCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectExchangeSender.class);

    private RabbitTemplate rabbitTemplate;

    /**
     * 设置回调类
     * @author wuyifan
     * @since 2018年3月19日
     */
    @Autowired
    public DirectExchangeSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }

    public void send(String msg) {

        String sendMsg = "hello1 " + new Date();
        System.out.println("Sender1 : " + sendMsg);
        CorrelationData data = new CorrelationData();
        data.setId(UUID.randomUUID().toString());
        this.rabbitTemplate.convertAndSend("user", "", sendMsg, data);

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        LOGGER.info("发送消息id: " + correlationData.getId());
        this.rabbitTemplate.convertAndSend(AmqpConfig.FOO_EXCHANGE, AmqpConfig.FOO_ROUTING_KEY, msg);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        LOGGER.info("发送方回调: {},{},{}", correlationData.getId(), ack, cause);
    }
}
