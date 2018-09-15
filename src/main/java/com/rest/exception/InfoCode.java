package com.rest.exception;

/**
 * @author zq
 * API 状态枚举类
 */
public enum InfoCode {
    SUCCESS(0,"成功"),
    FAIL(1,"失败"),
    SERVICE_UNAVAILABLE(5000,"服务异常，请稍后再试");
    private int status;

    private String msg;

    InfoCode(int status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
