package com.springboot.seckill.service.impl;

import com.springboot.seckill.dao.OrderVoDao;
import com.springboot.seckill.entity.Order;
import com.springboot.seckill.service.OrderVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderVoServiceImpl implements OrderVoService {

    @Autowired
    OrderVoDao orderVoDao;

    @Override
    public long createOrder(Order order) {
        return orderVoDao.createOrder(order);
    }

}
