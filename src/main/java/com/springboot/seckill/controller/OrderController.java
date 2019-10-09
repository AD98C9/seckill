package com.springboot.seckill.controller;

import com.springboot.seckill.dto.SeckillVo;
import com.springboot.seckill.entity.Order;
import com.springboot.seckill.rabbit.MessageProducer;
import com.springboot.seckill.rabbit.RabbitConfig;
import com.springboot.seckill.redis.RedisService;
import com.springboot.seckill.service.OrderVoService;
import com.springboot.seckill.service.SeckillVoService;
import com.springboot.seckill.util.JsonUtil;
import com.springboot.seckill.util.MsgUtil;
import com.springboot.seckill.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class OrderController {

    @Autowired
    private SeckillVoService seckillVoService;

    @Autowired
    private OrderVoService orderVoService;

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping("/post/orderVo/{seckillId}/{token}")
    @ResponseBody
    public String order(HttpServletRequest request,
                        HttpServletResponse response,
                        Model model,
                        @PathVariable("seckillId") Integer seckillId,
                        @PathVariable("token") String token,
                        @RequestBody(required = false) String json) {

        String html = redisService.getHtml(RedisService.ORDER_KEY+seckillId);
        if (html.equals("")) {
            SeckillVo seckillVo = seckillVoService.getSeckillVoBySeckillId(seckillId);
            model.addAttribute("seckillVo", seckillVo);
            WebContext ctx = new WebContext(request, response, request.getServletContext(),
                    request.getLocale(), model.asMap());
            html = thymeleafViewResolver.getTemplateEngine().process("order", ctx);
            redisService.setHtml(RedisService.ORDER_KEY+seckillId, html);
        }

        MsgUtil msgUtil = JsonUtil.stringToMsg(json);
        if (msgUtil == null) return html;
        String address = msgUtil.getValue().getString("address");
        Order order = new Order(null, new TokenUtil().getUser(token), seckillId, 0, address);
        if (address != null) {
            if (redisService.checkOrderKey(order.getSeckillId(), order.getPhone()).isIs()){
                messageProducer.sendMessageA(order);
            }
            else log.info("重复秒杀");
        }
        return html;
    }
}
