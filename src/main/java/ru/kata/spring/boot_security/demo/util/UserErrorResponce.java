package ru.kata.spring.boot_security.demo.util;

public class UserErrorResponce {

    private String message;
    private long errorTime;

    public UserErrorResponce(String message, long errorTime) {
        this.message = message;
        this.errorTime = errorTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(long errorTime) {
        this.errorTime = errorTime;
    }
}
