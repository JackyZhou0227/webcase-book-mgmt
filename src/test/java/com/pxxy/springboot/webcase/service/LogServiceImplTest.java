package com.pxxy.springboot.webcase.service;

import com.pxxy.springboot.webcase.entity.LogMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class LogServiceImplTest {

    @Autowired
    private LogService logService;

    @Test
    public void testFindAll() {
        List<LogMessage> all = logService.findAll();
        if(all != null) {
            all.forEach(System.out::println);
        }
    }

    @Test
    public void testFindById() {
        LogMessage byId = logService.findById(1);
        System.out.println(byId);
    }
}
