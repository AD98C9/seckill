package com.springboot.seckill.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SeckillVo {
    private Integer seckillId;
    private Integer commodityId;

    private String commodityName;
    private String commodityTitle;
    private String commodityDetail;
    private Double commodityPrice;
    private String commodityImg;

    private Double seckillPrice;
    private Date startTime;
    private Date endTime;
    private Integer seckillStock;

}
