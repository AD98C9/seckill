package com.springboot.seckill.controller;

import com.springboot.seckill.util.JsonUtil;
import com.springboot.seckill.util.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class TestController {

    @RequestMapping("/put/Commodity")
    public String addCommodity(@RequestBody String json){
        MsgUtil msgUtil = JsonUtil.stringToMsg(json);
        if(msgUtil == null) return "test";
        log.info("-------------------->" + msgUtil.getValue().getClass().getName());;
        return "test";
    }

    @RequestMapping("/put/Seckill")
    public String addSeckill(@RequestBody String json){
        return "test";
    }

}
