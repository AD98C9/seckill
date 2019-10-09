package com.springboot.seckill.rabbit;

import com.springboot.seckill.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageProducer implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessageA(Object message){
        log.info("ProducerA publish message------->:{}", message);
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> {
            if (!ack) {
                log.info("MessageProducer 消息发送失败" + cause + correlationData.toString());
            } else {
                log.info("MessageProducer 消息发送成功 ");
            }
        }));
        this.rabbitTemplate.convertAndSend(RabbitConfig.exchangeName, RabbitConfig.routingKeyA, JsonUtil.classToString(message).getBytes());
    }

    public void sendMessageB(Object message){
        log.info("ProducerB publish message------->:{}", message);
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> {
            if (!ack) {
                log.info("MessageProducer 消息发送失败" + cause + correlationData.toString());
            } else {
                log.info("MessageProducer 消息发送成功 ");
            }
        }));
        this.rabbitTemplate.convertAndSend(RabbitConfig.exchangeName, RabbitConfig.routingKeyB, JsonUtil.classToString(message).getBytes());
    }

    /*
    失败后return回调
     */
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("return--message:"+new String(message.getBody())+",replyCode:"+i+",replyText:"+s+",exchange:"+s1+",routingKey:"+s2);
    }

    /*
    确认后回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println("confirm--:correlationData:"+correlationData+",ack:"+b+",cause:"+s);
    }
}
