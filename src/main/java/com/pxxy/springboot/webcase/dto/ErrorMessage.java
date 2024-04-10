package com.pxxy.springboot.webcase.dto;

public class ErrorMessage {

    private String field;
    private String defaultMessage;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public ErrorMessage(String field, String defaultMessage) {
        this.field = field;
        this.defaultMessage = defaultMessage;
    }
    public ErrorMessage() {
    }
}
