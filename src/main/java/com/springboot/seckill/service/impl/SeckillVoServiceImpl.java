package com.springboot.seckill.service.impl;

import com.springboot.seckill.dao.SeckillVoDao;
import com.springboot.seckill.dto.SeckillVo;
import com.springboot.seckill.entity.Commodity;
import com.springboot.seckill.entity.Seckill;
import com.springboot.seckill.service.SeckillVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SeckillVoServiceImpl implements SeckillVoService {

    @Autowired
    private SeckillVoDao seckillVoDao;

    @Override
    public ArrayList<SeckillVo> getAllSeckillVo() {
        return seckillVoDao.getAllSeckillVo();
    }

    @Override
    public SeckillVo getSeckillVoBySeckillId(Integer seckillId) {
        return seckillVoDao.getSeckillVoBySeckillId(seckillId);
    }

    @Override
    public boolean reduceSekcillStock(Integer seckillId) {
        return seckillVoDao.reduceSekcillStock(seckillId) != 0;
    }

    @Override
    public long addCommodity(Commodity commodity) {
        return seckillVoDao.addCommodity(commodity);
    }

    @Override
    public long addSeckill(Seckill seckill) {
        return seckillVoDao.addSeckill(seckill);
    }

}
