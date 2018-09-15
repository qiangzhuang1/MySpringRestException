package com.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author wzq
 * 页面
 */
@Controller
@RequestMapping(value = "/page")
public class MyPageController {

    @RequestMapping(value = "/first",method = RequestMethod.GET)
    public Object first(){
        System.out.println("跳转页面");
/*        if(1==1){
            throw new PageException(500,"跳转页面失败");
        }*/
        return "account/accountPage";
    }


}