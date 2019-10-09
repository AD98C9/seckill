package com.springboot.seckill.dto;

import com.springboot.seckill.entity.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderVo {
    private Integer orderId;
    private String phone;
    private Integer seckillId;

    private Double seckillPrice;

    private String commodityName;
    private String commodityDetail;
    private Double commodityPrice;

    private Integer status = 0;
    private String address;

}
