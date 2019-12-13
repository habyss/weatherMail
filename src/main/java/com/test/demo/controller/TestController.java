package com.test.demo.controller;

import com.test.demo.entity.Test;
import com.test.demo.mapper.wf.WeatherConfigMapper;
import com.test.demo.service.impl.WeatherServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author kun.han on 2019/10/17 16:28
 */
@RestController
public class TestController {

    @Resource
    WeatherConfigMapper weatherConfigMapper;
    @Resource
    WeatherServiceImpl weatherService;

    public Test test(@Validated Test test){
        System.out.println(test.getBirthday());
        return test;
    }

    @GetMapping("test")
    public String test(){
        return weatherService.sendWeatherMail();
    }

}
