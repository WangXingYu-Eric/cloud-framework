package com.sinosoft.newstandard.common.enumeration;

/**
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public enum CustomStatus {

    SYSTEM_ERROR(4001, "系统异常"),
    SYSTEM_BUSY(4002, "系统繁忙,请稍候再试"),
    AUTH_ERROR(4003, "获取登录用户信息异常");

    CustomStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    private int value;

    private String reasonPhrase;

    public int value() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

}
