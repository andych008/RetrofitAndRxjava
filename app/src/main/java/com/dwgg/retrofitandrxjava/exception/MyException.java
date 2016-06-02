package com.dwgg.retrofitandrxjava.exception;

/**
 * app内自定义exception
 * Created by 喵叔catuncle
 * 2016/06/01 08:28
 */
public class MyException extends RuntimeException {
    private int code;

    public MyException(String detailMessage, int code) {
        super(detailMessage);
        this.code = code;
    }

    public MyException(String detailMessage, Throwable throwable, int code) {
        super(detailMessage, throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
