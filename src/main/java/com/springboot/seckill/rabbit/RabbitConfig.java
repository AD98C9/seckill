package com.springboot.seckill.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class RabbitConfig implements RabbitListenerConfigurer {

    public static final String exchangeName = "exchange";
    public static final String queueAName = "checkStock";
    public static final String queueBName = "queueB";
    public static final String routingKeyA = "order";
    public static final String routingKeyB = "msg";


    @Bean
    public Queue queueA(){
        return new Queue(queueAName, true);
    }

    @Bean
    public Queue queueB(){
        return new Queue(queueBName, true);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding bindingExchangeA(Queue queueA, DirectExchange directExchange) {
        return BindingBuilder.bind(queueA).to(directExchange).with(routingKeyA);
    }

    @Bean
    Binding bindingExchangeB(Queue queueB, DirectExchange directExchange) {
        return BindingBuilder.bind(queueB).to(directExchange).with(routingKeyB);
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

}