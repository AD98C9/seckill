package com.springboot.seckill.dao;

import com.springboot.seckill.entity.User;

public interface UserDao {
    User getUserByPhone(String phone);
}
