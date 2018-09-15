package com.rest.exception;


/**
 * @author wzq
 * 页面异常
 */
public class PageException extends RuntimeException {

    private InfoCode infoCode;

    private int code;

    public PageException(InfoCode infoCode){
        super(infoCode.getMsg());
        this.infoCode = infoCode;
    }

    public PageException(InfoCode infoCode, String msg){
        super(msg);
        this.infoCode = infoCode;
    }

    public PageException(int code, String msg){
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
