package com.jd.jr.tradeCenter.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangshuo7 on 2016/6/29.
 */
@Controller
@RequestMapping("/hello")
public class HelloWorldController {

    @RequestMapping(value = "/helloWorld",method = {RequestMethod.GET,RequestMethod.POST})
    public String helloWorld(){
        return "HelloWorld";
    }
}
