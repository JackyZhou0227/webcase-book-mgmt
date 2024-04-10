package com.pxxy.springboot.webcase.mapper;

import com.pxxy.springboot.webcase.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;

/*****
 * 用户数据的持久层
 */
@Mapper
public interface UserMapper {

    int save(User user);

    int update(User user);
    int delete(Serializable id);
    List<User> findAll();

    User findById(Serializable id);


    User findByUserName(String userName);
}
