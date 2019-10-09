package com.springboot.seckill.dao;


import com.springboot.seckill.entity.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderVoDao {

    long createOrder(@Param("order")Order order);

}
