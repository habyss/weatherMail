package com.test.demo.controller;

import com.test.demo.task.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author kun.han on 2019/6/12 17:07
 */
@RestController
public class TestWeatherController {
    @Resource
    WeatherService weatherService;

    @GetMapping("testWeather")
    public void test(){
        weatherService.getWeather();
    }
}
