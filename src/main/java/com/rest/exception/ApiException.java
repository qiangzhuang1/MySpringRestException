package com.rest.exception;

/**
 * @author wzq
 * api异常
 */
public class ApiException extends RuntimeException {

    private InfoCode infoCode;

    private int code;

    public ApiException(InfoCode infoCode){
        super(infoCode.getMsg());
        this.infoCode = infoCode;
    }

    public ApiException(InfoCode infoCode, String msg){
        super(msg);
        this.infoCode = infoCode;
    }

    public ApiException(int code, String msg){
        super(msg);
        this.code = code;
    }

    public InfoCode getInfoCode() {
        return infoCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
