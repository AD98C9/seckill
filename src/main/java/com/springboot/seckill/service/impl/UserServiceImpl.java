package com.springboot.seckill.service.impl;

import com.springboot.seckill.dao.UserDao;
import com.springboot.seckill.entity.User;
import com.springboot.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }
}
