package com.rest.file;

import com.alibaba.fastjson.annotation.JSONType;
import com.rest.exception.InfoCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author zq
 * 返回统一json
 */
@JSONType(orders = { "code", "msg","time", "data" })
public class RestResp implements Serializable {
    private static final long serialVersionUID = -3197616652643404121L;

    private int code;

    private String msg;

    private Long time;

    private Object data;

    public RestResp() {
    }

    public RestResp(InfoCode infoCode, Object data) {
        if (infoCode != null) {
            setCode(infoCode.getStatus());
            setMsg(infoCode.getMsg());
        }
        this.data = data;
    }

    public RestResp(InfoCode infoCode, String message) {
        if (infoCode != null) {
            setCode(infoCode.getStatus());
            if (StringUtils.isBlank(message)){
                setMsg(infoCode.getMsg());
            }else{
                setMsg(message);
            }
        }
    }

    public static RestResp build(InfoCode infoCode, Object data) {
        return new RestResp(infoCode, data);
    }

    public static RestResp build(InfoCode infoCode,String message) {
        return new RestResp(infoCode, message);
    }

    public static RestResp build(InfoCode infoCode) {
        return new RestResp(infoCode, null);
    }

    public static RestResp build(int status, String message, Object data) {
        RestResp resp = new RestResp();
        resp.setData(data);
        resp.setMsg(message);
        resp.setCode(status);
        return resp;
    }

    public static RestResp build(int status, String message ) {
        RestResp resp = new RestResp();
        resp.setMsg(message);
        resp.setCode(status);
        return resp;
    }

    public Long getTime() {
        return System.currentTimeMillis();
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
