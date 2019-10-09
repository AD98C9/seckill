package com.springboot.seckill.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String phone;
    private String password;
    private String salt;
}
