package com.pxxy.springboot.webcase.mapper;

import com.pxxy.springboot.webcase.entity.LogMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

@Mapper
public interface LogMapper {

    int save(LogMessage logMessage);

    int delete(Serializable id);

    List<LogMessage> findAll();

    LogMessage findById(Serializable id);

    List<LogMessage> findByKeyword(@Param("keyword") String keyword);

    Integer deleteSelected(List<Integer> idList);
}
