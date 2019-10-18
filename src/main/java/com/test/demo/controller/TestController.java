package com.test.demo.controller;

import com.test.demo.entity.Test;
import com.test.demo.mapper.wf.WeatherConfigMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author kun.han on 2019/10/17 16:28
 */
@RestController
public class TestController {

    @Resource
    WeatherConfigMapper weatherConfigMapper;

    @PostMapping("test")
    public Test test(Test test){
        System.out.println(test.getBirthday());
        return test;
    }
}
