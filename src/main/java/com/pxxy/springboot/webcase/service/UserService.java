package com.pxxy.springboot.webcase.service;


import com.pxxy.springboot.webcase.entity.User;

public interface UserService {

    User login(String userName, String pwd);

    void register(User user);

    boolean judgeUser(User user);
}
