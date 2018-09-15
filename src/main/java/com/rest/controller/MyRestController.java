package com.rest.controller;

import com.google.common.collect.Maps;
import com.rest.exception.ApiException;
import com.rest.exception.InfoCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zq
 * 接口
 */
@RestController
@RequestMapping(value = "/rest")
public class MyRestController {

    @RequestMapping(value = "/first",method = RequestMethod.GET)
    public Object first(@RequestParam(value="param",required = false) String param){
        Map<String,Object> resultMap = Maps.newHashMap();
        resultMap.put("name","返回接口数据");
        return resultMap;
    }


}