package com.pxxy.springboot.webcase.service.impl;

import com.pxxy.springboot.webcase.entity.LogMessage;
import com.pxxy.springboot.webcase.mapper.LogMapper;
import com.pxxy.springboot.webcase.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Resource
    private LogMapper logMapper;

    @Override
    public int save(LogMessage logMessage) {
        return logMapper.save(logMessage);
    }

    @Override
    public int delete(Serializable id) {
        return logMapper.delete(id);
    }

    @Override
    public List<LogMessage> findAll() {
        return logMapper.findAll();
    }

    @Override
    public LogMessage findById(Serializable id) {
        return logMapper.findById(id);
    }

    @Override
    public List<LogMessage> findByKeyword(String keyword) {
        return logMapper.findByKeyword(keyword);
    }

    @Override
    public Integer deleteSelected(List<Integer> idList) {
        return logMapper.deleteSelected(idList);
    }

}
