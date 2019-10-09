package com.springboot.seckill.service;

import com.springboot.seckill.dto.SeckillVo;
import com.springboot.seckill.entity.Commodity;
import com.springboot.seckill.entity.Seckill;

import java.util.ArrayList;

public interface SeckillVoService {

    ArrayList<SeckillVo> getAllSeckillVo();

    SeckillVo getSeckillVoBySeckillId(Integer seckillId);

    boolean reduceSekcillStock(Integer seckillId);

    long addCommodity(Commodity commodity);

    long addSeckill(Seckill seckill);
}
