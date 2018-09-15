package com.rest.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import com.rest.exception.InfoCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.util.Map;

/**
 * @author zq
 * 返回统计json
 */
@RestControllerAdvice
public class RestResponseBodyAdvice implements ResponseBodyAdvice {

    private static final Logger logger = LoggerFactory.getLogger(RestResponseBodyAdvice.class);

    private static final String REQUEST_ID = "request-id";
    private static final String OPERATION_COST_TIME = "cost-time";
    private static final String REQUEST_TIME = "request-time";
    private static final String RESPONSE_URL = "request-url";
    private static final String ENTITY = "entity";
    private static final String STATUS = "status";

    SerializerFeature[] features = new SerializerFeature[]{SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.DisableCircularReferenceDetect};

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                  MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        ServletServerHttpRequest req = null;
        ServletServerHttpResponse resp = null;

        if (serverHttpRequest instanceof ServletServerHttpRequest){
            req = (ServletServerHttpRequest)serverHttpRequest;
        }
        if (serverHttpResponse instanceof ServletServerHttpResponse){
            resp = (ServletServerHttpResponse)serverHttpResponse;
        }
        /*String url = req.getServletRequest().getServletPath();
        if (StringUtils.isNotBlank(url)){
            return o;
        }*/
        if (o instanceof RestResp){
            logger(req,resp,o);
            return o;
        }
        logger(req,resp,o);
        return new RestResp(InfoCode.SUCCESS,o);
    }

    private void logger(ServletServerHttpRequest req, ServletServerHttpResponse resp,Object body){
        Long requestId = (Long) req.getServletRequest().getAttribute(REQUEST_ID);
        Long reqTime = (Long) req.getServletRequest().getAttribute(REQUEST_TIME);
        Long costTime = System.currentTimeMillis()-reqTime;
        Map<String,Object> map = Maps.newHashMap();
        map.put(STATUS,resp.getServletResponse().getStatus());
        map.put(OPERATION_COST_TIME,costTime);
        map.put(REQUEST_ID,requestId);
        StringBuffer url = req.getServletRequest().getRequestURL();
        map.put(RESPONSE_URL,url);
        map.put(ENTITY,body);
        logger.info(JSON.toJSONString(map,features));
    }
}
