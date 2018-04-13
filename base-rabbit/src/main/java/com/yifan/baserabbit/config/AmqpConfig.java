package com.yifan.baserabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author wuyifan
 * @since 2017年12月20日
 */
@Configuration
public class AmqpConfig {
    public static final String FOO_EXCHANGE   = "callback.exchange.foo";
    public static final String FOO_ROUTING_KEY = "callback.routingkey.foo";
    public static final String FOO_QUEUE      = "callback.queue.foo";

    @Value("${spring.rabbitmq.addresses}")
    private String addresses;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;
    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisherConfirms;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        /* 如果要进行消息回调，则这里必须要设置为true */
        connectionFactory.setPublisherConfirms(publisherConfirms);
        return connectionFactory;
    }

    /**
     *  因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    /** 设置交换机类型  */
    @Bean
    public DirectExchange defaultExchange() {
        /*
         * DirectExchange:按照routing key分发到指定队列
         * TopicExchange:多关键字匹配
         * FanoutExchange: 将消息分发到所有的绑定队列，无routing key的概念
         * HeadersExchange ：通过添加属性key-value匹配
         */
        return new DirectExchange(AmqpConfig.FOO_EXCHANGE);
    }

    @Bean
    public Queue fooQueue() {
        return new Queue(AmqpConfig.FOO_QUEUE, true);
    }

    @Bean
    public Binding binding() {
        /* 将队列绑定到交换机 */
        return BindingBuilder
                .bind(fooQueue())
                .to(defaultExchange())
                .with(AmqpConfig.FOO_ROUTING_KEY);
    }


    @Bean
    public Queue fanoutQueue() {
        return new Queue("FanoutQueue2", true);
    }

    @Bean
    public FanoutExchange fanoutExchange () {
        return new FanoutExchange("FanoutExchange");
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }

}
