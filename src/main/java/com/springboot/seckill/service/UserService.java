package com.springboot.seckill.service;


import com.springboot.seckill.entity.User;

public interface UserService {

    User getUserByPhone(String phone);

}
