package com.pxxy.springboot.webcase.service.impl;

import com.pxxy.springboot.webcase.entity.User;
import com.pxxy.springboot.webcase.mapper.UserMapper;
import com.pxxy.springboot.webcase.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(String userName, String pwd) {
        if (userName != null) {
            return userMapper.findByUserName(userName);

        }
        return null;
    }

    @Override
    public void register(User user) {
        if (user == null) {
            throw new RuntimeException("parameter is null");
        }
        try {
            userMapper.save(user);
        } catch (RuntimeException e) {
            throw new RuntimeException("register failed");
        }
    }


    @Override
    public boolean judgeUser(User user) {
        String name = user.getUserName();
        User tempUser = userMapper.findByUserName(name);
        return tempUser != null;

    }
}
