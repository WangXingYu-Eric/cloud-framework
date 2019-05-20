package com.sinosoft.newstandard.common.exception;

import com.sinosoft.newstandard.common.enumeration.CustomStatus;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class BaseException extends RuntimeException {

    private int code;
    private String message;

    public BaseException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseException(CustomStatus customStatus) {
        this.code = customStatus.value();
        this.message = customStatus.getReasonPhrase();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
