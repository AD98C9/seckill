package com.springboot.seckill.redis;

import com.springboot.seckill.dto.SeckillVo;
import com.springboot.seckill.entity.Seckill;
import com.springboot.seckill.service.SeckillVoService;
import com.springboot.seckill.util.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class RedisService {

    public static String LOGIN_KEY = "html-login";
    public static String ORDER_KEY = "order:";
    public static String SECKILLVO_KEY = "seckillVo:";
    public static String SECKILLVOLIST_KEY = "seckillVoList";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SeckillVoService seckillVoService;

    public void addSeckill(SeckillVo seckillVo) {
        redisTemplate.opsForHash().put("seckill", seckillVo.getSeckillId().toString(), seckillVo);
        redisTemplate.opsForHash().put("stock", seckillVo.getSeckillId().toString(), seckillVo.getSeckillStock().toString());
    }

    public boolean updataStock(Integer seckillId){
        SeckillVo seckillVo = seckillVoService.getSeckillVoBySeckillId(seckillId);
        if(seckillVo == null) return false;
        redisTemplate.opsForHash().put("stock", seckillVo.getSeckillId().toString(), seckillVo.getSeckillStock().toString());
        return true;
    }

    public void addOrderKey(Integer seckillId, String phone) {
        redisTemplate.opsForSet().add("order", seckillId.toString() + ":" + phone);
    }

    public MsgUtil checkOrderKey(Integer seckillId, String phone) {
        if (seckillId == null || phone == null) return new MsgUtil("信息不足", null, false);
        boolean success = !redisTemplate.opsForSet().isMember("order", seckillId.toString() + ":" + phone);
        return new MsgUtil("首次秒杀", null, success);
    }

    public MsgUtil reduceStock(Integer seckillId) {
        // 如果redis为空，尝试补充库存
        if(!redisTemplate.opsForHash().hasKey("stock", seckillId.toString()))
            if(!this.updataStock(seckillId)) return new MsgUtil("错误的秒杀ID",null, false);

        DistributedRedisLock.acquire("stock");
        Integer stock = Integer.valueOf((String) Objects.requireNonNull(redisTemplate.opsForHash().get("stock", seckillId.toString())));
        if (stock > 0) redisTemplate.opsForHash().increment("stock", seckillId.toString(), -1);
        DistributedRedisLock.release("stock");
        log.info("-----> stock in redis is : " + (stock-1));

        if (stock > 0) return new MsgUtil("成功秒杀",null, true);
        return new MsgUtil("库存不足",null, false);
    }


    public void addToken(String phone, String token) {
        redisTemplate.opsForValue().append("token:" + phone, token);
        redisTemplate.expire("token:" + phone, 60, TimeUnit.MINUTES);
    }

    public boolean checkToken(String phone, String token) {
        String redisToken = redisTemplate.opsForValue().get("token:" + phone);
        if (redisToken != null && redisToken.equals(token)) {
            redisTemplate.expire("token:" + phone, 60, TimeUnit.MINUTES);
            return true;
        }
        return false;
    }

    public String getHtml(String key){
        String temp = redisTemplate.opsForValue().get(key);
        if(temp==null) return "";
        return temp;
    }

    public void setHtml(String key,String html){
        redisTemplate.opsForValue().append(key, html);
        redisTemplate.expire("key", 60, TimeUnit.SECONDS);
    }
}
