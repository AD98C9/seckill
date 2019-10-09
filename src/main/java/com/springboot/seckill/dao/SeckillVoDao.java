package com.springboot.seckill.dao;

import com.springboot.seckill.dto.SeckillVo;
import com.springboot.seckill.entity.Commodity;
import com.springboot.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface SeckillVoDao {

    ArrayList<SeckillVo> getAllSeckillVo();

    SeckillVo getSeckillVoBySeckillId(Integer seckillId);

    int reduceSekcillStock(Integer seckillId);

    long addCommodity(Commodity commodity);

    long addSeckill(Seckill seckill);
}
