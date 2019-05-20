package com.sinosoft.newstandard.common.web.entity;

import com.sinosoft.newstandard.common.enumeration.CustomStatus;
import com.sinosoft.newstandard.common.exception.BaseException;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class Result<T> extends BaseEntity {

    private static final long serialVersionUID = -7931039889075586117L;

    private int code;
    private String message;
    private Instant timestamp;
    private T data;

    public Result() {
    }

    public Result(CustomStatus customStatus) {
        this.code = customStatus.value();
        this.message = customStatus.getReasonPhrase();
        this.timestamp = ZonedDateTime.now().toInstant();
    }

    public Result(CustomStatus customStatus, T data) {
        this(customStatus);
        this.data = data;
    }

    public Result(HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.timestamp = ZonedDateTime.now().toInstant();
    }

    public Result(HttpStatus httpStatus, T data) {
        this(httpStatus);
        this.data = data;
    }

    /**
     * 内部使用,用于构造成功的结果.
     */
    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = ZonedDateTime.now().toInstant();
    }

    /**
     * 快速创建成功结果.
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 快速创建成功结果,并返回结果数据.
     */
    public static <T> Result success(T data) {
        return new Result<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    /**
     * 系统异常.
     */
    public static Result fail() {
        return fail(HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    /**
     * 系统异常.
     */
    public static Result fail(int code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 系统异常并返回结果数据.
     */
    public static <T> Result fail(T data) {
        return fail(HttpStatus.INTERNAL_SERVER_ERROR, data);
    }

    /**
     * 系统异常并返回结果数据.
     */
    public static <T> Result fail(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    /**
     * 系统异常并返回结果数据.
     */
    public static <T> Result fail(CustomStatus customStatus, T data) {
        return new Result<>(customStatus, data);
    }

    /**
     * 系统异常并返回结果数据.
     */
    public static <T> Result fail(HttpStatus httpStatus, T data) {
        return new Result<>(httpStatus, data);
    }

    /**
     * 系统异常.
     */
    public static Result fail(BaseException e) {
        return fail(e, null);
    }

    /**
     * 系统异常并返回结果数据.
     */
    public static <T> Result fail(BaseException e, T data) {
        return new Result<>(e.getCode(), e.getMessage(), data);
    }

    public boolean isSuccess() {
        return HttpStatus.OK.value() == this.code;
    }

    public boolean isFail() {
        return !isSuccess();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
