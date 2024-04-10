package com.pxxy.springboot.webcase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class LogMessage {
    //日志包含 主键ID、请求发起人【如果没有登录，则显示未登录】、请求URL， 请求时间【年月日时分秒】、请求所耗的时长【以ms为单位】。
    private Integer id;
    private String userName;
    private String url;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestTime;
    private long requestDuration;

    public LogMessage() {
    }
    public LogMessage(String userName, String url, LocalDateTime requestTime, long requestDuration) {
        this.userName = userName;
        this.url = url;
        this.requestTime = requestTime;
        this.requestDuration = requestDuration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public long getRequestDuration() {
        return requestDuration;
    }

    public void setRequestDuration(long requestDuration) {
        this.requestDuration = requestDuration;
    }

}
