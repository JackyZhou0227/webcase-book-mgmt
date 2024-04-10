package com.pxxy.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//启动 Servlet相关的组件，如：Filter
@ServletComponentScan
public class SpringbootTutorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTutorialApplication.class, args);
    }

}
