package com.springboot.seckill.util;

import com.springboot.seckill.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenUtil {

    @Autowired
    private RedisService redisService;

    public String createToken(String phone){
        UUID uuid = UUID.randomUUID();
        String token = phone +"-"+ uuid.toString();
        //将token存入redis
        redisService.addToken(phone, token);
        return token;
    }

    public boolean checkToken(String token){
        if(token==null || "".equals(token)){
            return false;
        }
        return redisService.checkToken(getUser(token), token);
    }

    public String getUser(String token){
        if(token==null || "".equals(token)){
            return null;
        }
        return token.split("-")[0];
    }

}
