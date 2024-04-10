package com.pxxy.springboot.webcase.entity;

public class BookOperationResponse {
    private String status;
    private Object data;

    public BookOperationResponse() {
    }

    public BookOperationResponse(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
