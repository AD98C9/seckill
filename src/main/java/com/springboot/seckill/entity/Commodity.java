package com.springboot.seckill.entity;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Commodity {
    private Integer commodityId;

    private String commodityName;
    private String commodityTitle;
    private String commodityDetail;
    private Double commodityPrice;
    private String commodityImg;
    private Integer commodityStock;
}
