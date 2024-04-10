package com.pxxy.springboot.webcase.service;

import com.pxxy.springboot.webcase.entity.LogMessage;

import java.io.Serializable;
import java.util.List;

public interface LogService {

    int save(LogMessage logMessage);

    int delete(Serializable id);

    List<LogMessage> findAll();

    LogMessage findById(Serializable id);

    List<LogMessage> findByKeyword(String keyword);

    Integer deleteSelected(List<Integer> idList);
}
