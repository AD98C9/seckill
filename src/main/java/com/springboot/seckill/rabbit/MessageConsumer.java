package com.springboot.seckill.rabbit;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.springboot.seckill.entity.Order;
import com.springboot.seckill.util.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import com.springboot.seckill.dto.OrderVo;
import com.springboot.seckill.redis.RedisService;
import com.springboot.seckill.service.OrderVoService;
import com.springboot.seckill.service.SeckillVoService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component

public class MessageConsumer{
    @Autowired
    SeckillVoService seckillVoService;

    @Autowired
    OrderVoService orderVoService;

    @Autowired
    RedisService redisService;

    @Autowired
    MessageProducer messageProducer;

    @RabbitListener(queues = RabbitConfig.queueAName)
    public void process(Channel channel, Message message) throws Exception {
        log.info("开始处理订单");
        Order order = JSON.parseObject(new String(message.getBody()), Order.class);
        try{
            //redis库存查询
            if (!redisService.reduceStock(order.getSeckillId()).isIs()) {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
                return ;
            }
            //添加防止重复秒杀KEY
            redisService.addOrderKey(order.getSeckillId(), order.getPhone());
            //创建订单
            orderVoService.createOrder(order);
            //库存消减
            seckillVoService.reduceSekcillStock(order.getSeckillId());

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

            log.info("订单处理成功");

        } catch (Exception e) {
            log.info("consumer receive message fall ------->:" + message);
            e.printStackTrace();
        }
    }

}
