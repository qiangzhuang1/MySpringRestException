package com.rest.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zq
 * 请求接口打印的具体参数
 */
public class ReqFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(ReqFilter.class);

    private final AtomicLong id = new AtomicLong(0);

    private static final String REQUEST_ID = "request-id";
    private static final String REQUEST_TIME = "request-time";
    private static final String URL = "url";
    private static final String METHOD = "method";
    private static final String HEAD = "head";
    private static final String COOKIES = "cookies";
    private static final String ENTITY = "entity";

    SerializerFeature[] features = new SerializerFeature[]{SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.DisableCircularReferenceDetect};

    private static final List<String> HEAD_NOT_INCLUDE = Arrays.asList("Accept", "Accept-Encoding", "Accept-Charset",
            "Accept-Language", "Connection", "Content-Encoding", "Content-Type", "Vary", "Cache-Control", "Cookie",
            "Host", "accept", "accept-encoding", "accept-charset", "accept-language", "connection", "content-encoding",
            "content-type", "vary", "cache-control", "cookie", "host");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //允许跨域访问
        HttpServletResponse rep = (HttpServletResponse)response;
        HttpServletRequest requ =  (HttpServletRequest)req;
        String header = requ.getHeader("Origin");
        rep.setHeader("Access-Control-Allow-Origin", header);
        rep.setHeader("Access-Control-Allow-Headers", "content-type");
        rep.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        rep.setHeader("Access-Control-Max-Age", "3628800");
        rep.setHeader("Allow", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        rep.setHeader("Access-Control-Allow-Credentials", "true");
        if("OPTIONS".equals(requ.getMethod())) {
            rep.setHeader("Content-Lengths", "0");
            rep.setHeader("Vary", "Origin");
            return;
        }
        HttpServletRequest request = null;
        if (!(req instanceof HttpServletRequest)){
            chain.doFilter(req,response);
            return;
        }
        request = (HttpServletRequest)req;
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }

        Map<String,Object> param = Maps.newHashMap();
        Map<String,Object> map = Maps.newHashMap();
        Long requestId = id.getAndIncrement();
        param.put(REQUEST_ID,requestId);
        map.put(REQUEST_ID,requestId);
        param.put(METHOD,request.getMethod());
        StringBuffer url = request.getRequestURL();
        map.put(METHOD,request.getMethod());
        Long requestTime = System.currentTimeMillis();
        param.put(REQUEST_TIME,requestTime);
        request.setAttribute(REQUEST_ID,requestId);
        request.setAttribute(REQUEST_TIME,requestTime);
        param.put(URL,url);
        String queryString = request.getQueryString();
        if (StringUtils.isNotBlank(queryString)){
            url = url.append("?").append(request.getQueryString());
        }
        map.put(URL,url);
        Enumeration<String> e = request.getHeaderNames();
        Map<String, Object> headers = Maps.newHashMap();
        while (e.hasMoreElements()) {
            String headName = e.nextElement();
            if (!HEAD_NOT_INCLUDE.contains(headName)){
                headers.put(headName, request.getHeader(headName));
            }
        }

        map.put(HEAD,headers);
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length>0){
            Map<String,Object> m = Maps.newHashMap();
            for (Cookie c : cookies) {
                m.put(c.getName(),c.getValue());
            }
            map.put(COOKIES,m);
        }

        ContentCachingRequestWrapper wrapper =
                WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            Enumeration<String> enumeration = wrapper.getParameterNames();
            Map<String,Object> entityBody = Maps.newHashMap();
            while (enumeration.hasMoreElements()){
                String paramName = enumeration.nextElement();
                entityBody.put(paramName,request.getParameter(paramName));
            }
            map.put(ENTITY,entityBody);
        }
        logger.info(JSON.toJSONString(map,features));
        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }
}
