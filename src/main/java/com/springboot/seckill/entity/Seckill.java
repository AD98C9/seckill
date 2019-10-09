package com.springboot.seckill.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Seckill {
    private Integer seckillId;
    private String commodityId;

    private Double seckillPrice;
    private Date startTime;
    private Date endTime;
    private Integer seckillStock;
}
