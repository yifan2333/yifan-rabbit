package com.yifan.rabbitconsumer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * @author wuyifan
 * @since 2017年12月20日
 */
@Configurable
public class Config {

    @Bean
    public Queue fooQueue() {
        return new Queue("callback.queue.foo", true);
    }

    @Bean
    public Queue fanoutQueue() {
        Queue queue = new Queue("FanoutQueue");
        queue.setShouldDeclare(true);
        return queue;
    }

    @Bean
    public FanoutExchange fanoutExchange () {
        return new FanoutExchange("FanoutExchange");
    }

    @Bean
    public Binding binding2(Queue fooQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fooQueue).to(fanoutExchange);
    }

    @Bean
    public Binding binding(Queue fanoutQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue).to(fanoutExchange);
    }
}
