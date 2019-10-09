package com.springboot.seckill.controller;


import com.springboot.seckill.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import com.springboot.seckill.dto.SeckillVo;
import com.springboot.seckill.service.SeckillVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Time;
import java.util.Date;


@Controller
@Slf4j
public class SeckillController {
    @Autowired
    private SeckillVoService seckillVoService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping("/get/seckillVo/list/{token}")
    @ResponseBody
    public String seckillVoList(HttpServletRequest request,
                                HttpServletResponse response,
                                Model model,
                                @PathVariable(value = "token") String token) {

        String html = redisService.getHtml(RedisService.SECKILLVOLIST_KEY);
        if(!html.equals("")){
            return html;
        }

        model.addAttribute("sekcillVoList", seckillVoService.getAllSeckillVo());

        WebContext ctx = new WebContext(request,response,request.getServletContext(),
                request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("sekcillVoList",ctx);
        redisService.setHtml(RedisService.SECKILLVOLIST_KEY, html);

        return html;
    }

    @RequestMapping("/get/seckillVo/{id}/{token}")
    @ResponseBody
    public String seckill(HttpServletRequest request,
                          HttpServletResponse response,
                          Model model,
                          @PathVariable("id") Integer seckillId,
                          @PathVariable("token") String token) {

        String html = redisService.getHtml(RedisService.SECKILLVO_KEY + seckillId);
        if(!html.equals("")){
            return html;
        }

        SeckillVo seckillVo = seckillVoService.getSeckillVoBySeckillId(seckillId);
        model.addAttribute("seckillVo", seckillVo);
        model.addAttribute("time", new Date().getTime());

        WebContext ctx = new WebContext(request,response,request.getServletContext(),
                request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("seckillVo",ctx);
        redisService.setHtml(RedisService.SECKILLVO_KEY + seckillId, html);

        return html;
    }

}
