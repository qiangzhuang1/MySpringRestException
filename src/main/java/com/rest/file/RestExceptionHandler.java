package com.rest.file;

import com.rest.exception.ApiException;
import com.rest.exception.InfoCode;
import com.rest.exception.PageException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zq
 * 异常返回类
 */
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public RestResp errorHandler(Exception ex) {
        if (ex instanceof ApiException){
            return RestResp.build(((ApiException) ex).getInfoCode(),ex.getMessage());
        }
        if (ex instanceof PageException){
            return RestResp.build(((PageException) ex).getCode(),ex.getMessage());
        }
        return RestResp.build(InfoCode.SERVICE_UNAVAILABLE);
    }
}
