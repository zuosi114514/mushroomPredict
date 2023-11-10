package com.sudoerrr.mushroom.core.controller;

import com.sudoerrr.mushroom.core.wrapper.ResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * test
 */
@Slf4j
@RestController
@SpringBootApplication
public class IndexController extends BaseController{



    @RequestMapping("getUser")
    public ResultWrapper getUser(){
        log.info("微信小程序正在调用...");
        Map<String,Object> data = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        list.add("Amy");
        list.add("Cathy");
        data.put("list",list);
        return ResultWrapper.success(data);
    }

    @RequestMapping("")
    public String getTest(){
        System.out.println("index innner");
        return "Hello world";
    }



}
