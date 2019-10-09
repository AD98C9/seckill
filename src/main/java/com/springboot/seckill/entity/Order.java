package com.springboot.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private Integer orderId;
    private String phone;
    private Integer seckillId;

    /**
     * 0:等待支付
     */
    private Integer status = 0;
    private String address;
}
